package com.wenxt.crm.service;

import com.wenxt.crm.model.*;
import com.wenxt.crm.repository.*;
import com.wenxt.crm.util.RemainderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SalesLeadDashboardService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RemainderUtility remainderUtility;

    // Helper method to create a standardized response
    private Map<String, Object> createResponse(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    // GET method for dashboard statistics
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            long totalCount = leadRepository.count();
            long newlyAdded = leadRepository.countByLeadStatus("Todo");
            long onPriority = leadRepository.countByLeadPriority("High");
            long completed = leadRepository.countByLeadStatus("Done");

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalCount", totalCount);
            stats.put("newlyAdded", newlyAdded);
            stats.put("onPriority", onPriority);
            stats.put("completed", completed);

            return ResponseEntity.ok(createResponse("success", "Stats fetched successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to fetch stats: " + e.getMessage(), null));
        }
    }

//    // GET method for activities (replaces separate events/appointments methods)
//    public ResponseEntity<Map<String, Object>> getActivities(String activityType) {
//        try {
//            List<ActivityModel> activities;
//            
//            if (activityType != null && !activityType.isEmpty()) {
//                // Filter by type if specified
//                activities = activityRepository.findByActivityTypeOrderByActivityDateAsc(activityType);
//            } else {
//                // Get all activities regardless of type
//                activities = activityRepository.findAll();
//            }
//            
//            return ResponseEntity.ok(createResponse("success", "Activities fetched successfully", activities));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(createResponse("error", "Failed to fetch activities: " + e.getMessage(), null));
//        }
//    }

    // GET method for activity logs
    public ResponseEntity<Map<String, Object>> getActivityLog() {
        try {
            List<ActivityLogModel> activityLogs = activityLogRepository.findAllByOrderByActivityDateDesc();
            return ResponseEntity.ok(createResponse("success", "Activity logs fetched successfully", activityLogs));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to fetch activity logs: " + e.getMessage(), null));
        }
    }

    // GET method for tasks
    public ResponseEntity<Map<String, Object>> getTasks() {
        try {
            List<TaskModel> tasks = taskRepository.findAllByOrderByTaskDueDateAsc();
            return ResponseEntity.ok(createResponse("success", "Tasks fetched successfully", tasks));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to fetch tasks: " + e.getMessage(), null));
        }
    }

    // GET method for calendar data
    public ResponseEntity<Map<String, Object>> getCalendarData(Date startDate, Date endDate) {
        try {
            // Get all activities in date range
            List<ActivityModel> activities = activityRepository.findByActivityDateBetween(startDate, endDate);
            
            // Filter activities by type
            List<ActivityModel> events = new ArrayList<>();
            List<ActivityModel> appointments = new ArrayList<>();
            
            for (ActivityModel activity : activities) {
                if ("EVENT".equals(activity.getActivityType())) {
                    events.add(activity);
                } else if ("APPOINTMENT".equals(activity.getActivityType())) {
                    appointments.add(activity);
                }
            }
            
            List<TaskModel> tasks = taskRepository.findByTaskDueDateBetween(startDate, endDate);

            Map<String, Object> calendarData = new HashMap<>();
            calendarData.put("events", events);
            calendarData.put("appointments", appointments);
            calendarData.put("tasks", tasks);

            return ResponseEntity.ok(createResponse("success", "Calendar data fetched successfully", calendarData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to fetch calendar data: " + e.getMessage(), null));
        }
    }

    // GET method for sales graph data
    public ResponseEntity<Map<String, Object>> getSalesGraphData() {
        try {
            List<GraphDataModel> graphDataList = new ArrayList<>();
            List<Object[]> leadCountsByMonth = leadRepository.countLeadsByMonth();

            for (Object[] result : leadCountsByMonth) {
                int monthNumber = (int) result[0];
                Long leads = (Long) result[1];
                String monthName = getMonthName(monthNumber);
                graphDataList.add(new GraphDataModel(monthName, leads.intValue()));
            }

            String[] allMonths = {"January", "February", "March", "April", "May", "June", 
                                  "July", "August", "September", "October", "November", "December"};
            for (String month : allMonths) {
                boolean monthExists = graphDataList.stream().anyMatch(data -> data.getMonth().equals(month));
                if (!monthExists) {
                    graphDataList.add(new GraphDataModel(month, 0));
                }
            }

            graphDataList.sort((a, b) -> Integer.compare(getMonthIndex(a.getMonth()), getMonthIndex(b.getMonth())));

            return ResponseEntity.ok(createResponse("success", "Sales graph data fetched successfully", graphDataList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to fetch sales graph data: " + e.getMessage(), null));
        }
    }

//    // POST method to create a new activity (replaces createEvent and createAppointment)
//    public ResponseEntity<Map<String, Object>> createActivity(ActivityModel activity) {
//        try {
//            // Validate activityType
//            if (!"APPOINTMENT".equals(activity.getActivityType()) && !"EVENT".equals(activity.getActivityType())) {
//                throw new IllegalArgumentException("Invalid activityType. Must be 'APPOINTMENT' or 'EVENT'.");
//            }
//
//            // Event-specific validations
//            if ("EVENT".equals(activity.getActivityType())) {
//                if (activity.getEventStartTime() == null || activity.getEventEndTime() == null) {
//                    throw new IllegalArgumentException("Event start/end time is required for events.");
//                }
//            }
//
//            // Appointment-specific validations
//            if ("APPOINTMENT".equals(activity.getActivityType())) {
//                if (activity.getActivityDuration() == null) {
//                    // Set default duration if none provided
//                    activity.setActivityDuration(30);
//                }
//            }
//
//            ActivityModel savedActivity = activityRepository.save(activity);
//            String logMessage = "Activity created: " + activity.getActivitySubject();
//            remainderUtility.sendRemainderAndLog(
//                "ACTIVITY_CREATED",
//                logMessage,
//                activity.getActivityDate() != null ? 
//                    new Date(activity.getActivityDate().getTime() - (4 * 3600 * 1000)) : null
//            );
//
//            return ResponseEntity.ok(createResponse("success", "Activity created successfully", savedActivity));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(createResponse("error", "Failed to create activity: " + e.getMessage(), null));
//        }
//    }

    // POST method to create a new activity log
    public ResponseEntity<Map<String, Object>> createActivityLog(ActivityLogModel activityLog) {
        try {
            ActivityLogModel savedActivityLog = activityLogRepository.save(activityLog);
            remainderUtility.sendRemainderAndLog(
                    "ACTIVITY_LOG_CREATED",
                    "Activity log created: " + activityLog.getActivityDescription(),
                    null
            );
            return ResponseEntity.ok(createResponse("success", "Activity log created successfully", savedActivityLog));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to create activity log: " + e.getMessage(), null));
        }
    }

    // POST method to create a new task
    public ResponseEntity<Map<String, Object>> createTask(TaskModel task) {
        try {
            TaskModel savedTask = taskRepository.save(task);
            remainderUtility.sendRemainderAndLog(
                    "TASK_CREATED",
                    "Task created: " + task.getTaskName(),
                    task.getTaskDueDate() != null ? new Date(task.getTaskDueDate().getTime() - (4 * 3600 * 1000)) : null
            );
            return ResponseEntity.ok(createResponse("success", "Task created successfully", savedTask));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to create task: " + e.getMessage(), null));
        }
    }

//    // PUT method to update an activity
//    public ResponseEntity<Map<String, Object>> updateActivity(Long id, ActivityModel activity) {
//        try {
//            ActivityModel existingActivity = activityRepository.findById(id.intValue())
//                .orElseThrow(() -> new RuntimeException("Activity not found"));
//
//            // Cannot change the type of an existing activity
//            if (!existingActivity.getActivityType().equals(activity.getActivityType())) {
//                throw new IllegalArgumentException("Cannot change the activity type of an existing activity.");
//            }
//
//            // Update common fields
//            existingActivity.setActivitySubject(activity.getActivitySubject());
//            existingActivity.setActivityDescription(activity.getActivityDescription());
//            existingActivity.setActivityDate(activity.getActivityDate());
//            existingActivity.setActivityStatus(activity.getActivityStatus());
//            existingActivity.setProbabilityPercentage(activity.getProbabilityPercentage());
//            existingActivity.setLoggedTime(activity.getLoggedTime());
//            existingActivity.setOriginalEstimate(activity.getOriginalEstimate());
//            
//            // Update type-specific fields
//            if ("EVENT".equals(existingActivity.getActivityType())) {
//                existingActivity.setEventStartTime(activity.getEventStartTime());
//                existingActivity.setEventEndTime(activity.getEventEndTime());
//                existingActivity.setEventEndDate(activity.getEventEndDate());
//                existingActivity.setEventLocation(activity.getEventLocation());
//                existingActivity.setEventOrganizer(activity.getEventOrganizer());
//                existingActivity.setEventPriority(activity.getEventPriority());
//                existingActivity.setReminderSent(activity.isReminderSent());
//            } else if ("APPOINTMENT".equals(existingActivity.getActivityType())) {
//                existingActivity.setActivityDuration(activity.getActivityDuration());
//            }
//
//            // Set updated info
//            existingActivity.setActivityUpdatedDate(new Date());
//            if (activity.getActivityUpdatedBy() != null) {
//                existingActivity.setActivityUpdatedBy(activity.getActivityUpdatedBy());
//            }
//
//            ActivityModel updatedActivity = activityRepository.save(existingActivity);
//            
//            // Send a reminder and log the update
//            remainderUtility.sendRemainderAndLog(
//                "ACTIVITY_UPDATED",
//                "Activity updated: " + activity.getActivitySubject(),
//                activity.getActivityDate() != null ? 
//                    new Date(activity.getActivityDate().getTime() - (4 * 3600 * 1000)) : null
//            );
//
//            return ResponseEntity.ok(createResponse("success", "Activity updated successfully", updatedActivity));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(createResponse("error", "Failed to update activity: " + e.getMessage(), null));
//        }
//    }

    // PUT method to update an activity log
    public ResponseEntity<Map<String, Object>> updateActivityLog(Long id, ActivityLogModel activityLog) {
        try {
            ActivityLogModel existingActivityLog = activityLogRepository.findById(id).orElseThrow(() -> new RuntimeException("Activity log not found"));
            existingActivityLog.setActivityDescription(activityLog.getActivityDescription());
            existingActivityLog.setActivityDate(activityLog.getActivityDate());
            ActivityLogModel updatedActivityLog = activityLogRepository.save(existingActivityLog);
            return ResponseEntity.ok(createResponse("success", "Activity log updated successfully", updatedActivityLog));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to update activity log: " + e.getMessage(), null));
        }
    }

    // PUT method to update a task
    public ResponseEntity<Map<String, Object>> updateTask(Long id, TaskModel task) {
        try {
            TaskModel existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
            existingTask.setTaskName(task.getTaskName());
            existingTask.setTaskDueDate(task.getTaskDueDate());
            TaskModel updatedTask = taskRepository.save(existingTask);
            return ResponseEntity.ok(createResponse("success", "Task updated successfully", updatedTask));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to update task: " + e.getMessage(), null));
        }
    }

//    // DELETE method to delete an activity
//    public ResponseEntity<Map<String, Object>> deleteActivity(Long id) {
//        try {
//            activityRepository.deleteById(id.intValue());
//            return ResponseEntity.ok(createResponse("success", "Activity deleted successfully", null));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(createResponse("error", "Failed to delete activity: " + e.getMessage(), null));
//        }
//    }

    // DELETE method to delete an activity log
    public ResponseEntity<Map<String, Object>> deleteActivityLog(Long id) {
        try {
            activityLogRepository.deleteById(id);
            return ResponseEntity.ok(createResponse("success", "Activity log deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to delete activity log: " + e.getMessage(), null));
        }
    }

    // DELETE method to delete a task
    public ResponseEntity<Map<String, Object>> deleteTask(Long id) {
        try {
            taskRepository.deleteById(id);
            return ResponseEntity.ok(createResponse("success", "Task deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createResponse("error", "Failed to delete task: " + e.getMessage(), null));
        }
    }

    // Helper method to get month name from month number
    private String getMonthName(int monthNumber) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", 
                               "July", "August", "September", "October", "November", "December"};
        return monthNames[monthNumber - 1];
    }

    // Helper method to get month index from month name
    private int getMonthIndex(String month) {
        String[] months = {"January", "February", "March", "April", "May", "June", 
                           "July", "August", "September", "October", "November", "December"};
        for (int i = 0; i < months.length; i++) {
            if (months[i].equalsIgnoreCase(month)) {
                return i;
            }
        }
        return -1;
    }
}