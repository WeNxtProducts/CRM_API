// ActivityService.java
package com.wenxt.crm.service;

import com.wenxt.crm.model.ActivityModel;
import com.wenxt.crm.model.EnquiryModel;
import com.wenxt.crm.repository.ActivityRepository;
import com.wenxt.crm.repository.EnquiryRepository;
import com.wenxt.crm.util.RemainderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private RemainderUtility remainderUtility;

    @Transactional
    public ActivityModel createActivity(ActivityModel activity) {
        validateActivity(activity);
        setEnquiryDetails(activity);
        setDefaultCreatedDate(activity);
        calculateDuration(activity);
        ActivityModel savedActivity = activityRepository.save(activity);
        sendActivityReminder(savedActivity, "ACTIVITY_CREATED");
        return savedActivity;
    }

    @Transactional
    public ActivityModel updateActivity(Integer id, ActivityModel activity) {
        ActivityModel existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + id));

        validateActivityTypeConsistency(existingActivity, activity);
        updateActivityFields(existingActivity, activity);
        validateActivity(existingActivity);
        calculateDuration(existingActivity);
        ActivityModel updatedActivity = activityRepository.save(existingActivity);
        sendActivityReminder(updatedActivity, "ACTIVITY_UPDATED");
        return updatedActivity;
    }

    private void validateActivity(ActivityModel activity) {
        if (activity.getActivityType() == null) {
            throw new IllegalArgumentException("Activity type is required");
        }

        if (!"APPOINTMENT".equals(activity.getActivityType()) && !"EVENT".equals(activity.getActivityType())) {
            throw new IllegalArgumentException("Invalid activityType. Must be 'APPOINTMENT' or 'EVENT'.");
        }

        if ("APPOINTMENT".equals(activity.getActivityType())) {
            if (activity.getEnquiry() == null || activity.getEnquiry().getEnqSeqNo() == null) {
                throw new IllegalArgumentException("Enquiry is required for appointments");
            }
            if (activity.getActivityDuration() == null) {
                activity.setActivityDuration(30); // Default duration for appointments
            }
        } else if ("EVENT".equals(activity.getActivityType())) {
            if (activity.getEventStartTime() == null || activity.getEventEndTime() == null) {
                throw new IllegalArgumentException("Event start/end time is required");
            }
            if (activity.getEnquiry() != null) {
                throw new IllegalArgumentException("Events cannot be linked to enquiries");
            }
        }
    }

    private void validateActivityTypeConsistency(ActivityModel existing, ActivityModel updated) {
        if (!existing.getActivityType().equals(updated.getActivityType())) {
            throw new IllegalArgumentException("Cannot change activity type");
        }
    }

    private void setEnquiryDetails(ActivityModel activity) {
        if ("APPOINTMENT".equals(activity.getActivityType()) && activity.getEnquiry() != null) {
            EnquiryModel enquiry = enquiryRepository.findById(activity.getEnquiry().getEnqSeqNo())
                    .orElseThrow(() -> new IllegalArgumentException("Enquiry not found"));
            activity.setEnquiry(enquiry);
        }
    }

    private void setDefaultCreatedDate(ActivityModel activity) {
        if (activity.getActivityCreatedDate() == null) {
            activity.setActivityCreatedDate(new Date());
        }
    }

    private void calculateDuration(ActivityModel activity) {
        if (activity.getEnquiry() != null && activity.getEnquiry().getEnqCreatedDate() != null) {
            String duration = calculateDurationBetweenDates(
                    activity.getEnquiry().getEnqCreatedDate(),
                    activity.getActivityCreatedDate()
            );
            activity.setDurationBetweenLeadAndLogTime(duration);
        }
    }

    private String calculateDurationBetweenDates(Date start, Date end) {
        LocalDateTime startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(startDate, endDate);
        return String.format("%dd %dh %dm", duration.toDays(), duration.toHoursPart(), duration.toMinutesPart());
    }

    private void sendActivityReminder(ActivityModel activity, String logType) {
        Date reminderDate = activity.getActivityDate() != null 
                ? new Date(activity.getActivityDate().getTime() - (4 * 3600 * 1000)) 
                : null;
        remainderUtility.sendRemainderAndLog(
                logType,
                "Activity " + (logType.equals("ACTIVITY_CREATED") ? "created" : "updated") + ": " + activity.getActivitySubject(),
                reminderDate
        );
    }

    public Optional<ActivityModel> getActivityById(Integer id) {
        return activityRepository.findById(id).map(activity -> {
            calculateDuration(activity);
            return activity;
        });
    }

    public List<ActivityModel> getAllActivities(String activityType) {
        if (activityType != null && !activityType.isEmpty()) {
            return activityRepository.findByActivityTypeOrderByActivityDateAsc(activityType);
        }
        return activityRepository.findAll();
    }

    @Transactional
    public void deleteActivity(Integer id) {
        ActivityModel activity = activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));
        activityRepository.delete(activity);
        remainderUtility.sendRemainderAndLog(
                "ACTIVITY_DELETED",
                "Activity deleted: " + activity.getActivitySubject(),
                null
        );
    }

    private void updateActivityFields(ActivityModel existing, ActivityModel updated) {
        existing.setActivitySubject(updated.getActivitySubject() != null ? updated.getActivitySubject() : existing.getActivitySubject());
        existing.setActivityDescription(updated.getActivityDescription() != null ? updated.getActivityDescription() : existing.getActivityDescription());
        existing.setActivityDate(updated.getActivityDate() != null ? updated.getActivityDate() : existing.getActivityDate());
        existing.setActivityStatus(updated.getActivityStatus() != null ? updated.getActivityStatus() : existing.getActivityStatus());
        existing.setProbabilityPercentage(updated.getProbabilityPercentage() != null ? updated.getProbabilityPercentage() : existing.getProbabilityPercentage());
        existing.setOriginalEstimate(updated.getOriginalEstimate() != null ? updated.getOriginalEstimate() : existing.getOriginalEstimate());
        existing.setActivityUpdatedDate(new Date());

        if ("EVENT".equals(existing.getActivityType())) {
            existing.setEventStartTime(updated.getEventStartTime() != null ? updated.getEventStartTime() : existing.getEventStartTime());
            existing.setEventEndTime(updated.getEventEndTime() != null ? updated.getEventEndTime() : existing.getEventEndTime());
            existing.setEventLocation(updated.getEventLocation() != null ? updated.getEventLocation() : existing.getEventLocation());
            existing.setEventOrganizer(updated.getEventOrganizer() != null ? updated.getEventOrganizer() : existing.getEventOrganizer());
        } else {
            existing.setActivityDuration(updated.getActivityDuration() != null ? updated.getActivityDuration() : existing.getActivityDuration());
        }
    }
}