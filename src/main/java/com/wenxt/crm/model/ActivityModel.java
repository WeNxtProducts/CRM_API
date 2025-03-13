package com.wenxt.crm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "WN_ACTIVITY")
public class ActivityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_SEQ_NO")
    private Integer activitySeqNo;

    @Size(max = 50, message = "Activity reference ID must be at most 50 characters")
    @Column(name = "ACTIVITY_REF_ID", length = 50)
    private String activityRefId;

    @ManyToOne
    @JoinColumn(name = "ENQ_SEQ_NO", referencedColumnName = "ENQ_SEQ_NO")
    private EnquiryModel enquiry;

    @NotBlank(message = "Activity type is required")
    @Size(max = 50, message = "Activity type must be at most 50 characters")
    @Column(name = "ACTIVITY_TYPE")
    private String activityType; // "APPOINTMENT" or "EVENT"

    
    @Size(max = 255, message = "Activity subject must be at most 255 characters")
    @Column(name = "ACTIVITY_SUBJECT", length = 255)
    private String activitySubject;

    @Lob
    @Column(name = "ACTIVITY_DESCRIPTION")
    private String activityDescription;

    @NotNull(message = "Activity date is required")
    @FutureOrPresent(message = "Activity date must be in the present or future")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACTIVITY_DATE")
    private Date activityDate;

    @Column(name = "ACTIVITY_DURATION", columnDefinition = "INT DEFAULT 30")
    private Integer activityDuration; // Duration in minutes

    @Column(name = "ACTIVITY_STATUS")
    private String activityStatus;

    @Column(name = "LOGGED_TIME", length = 20)
    private String loggedTime;

    @Column(name = "ORIGINAL_ESTIMATE", length = 20)
    private String originalEstimate;

    @Column(name = "PROBABILITY_PERCENTAGE")
    private Integer probabilityPercentage;

    @Column(name = "ACTIVITY_CREATED_BY", length = 50)
    private String activityCreatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACTIVITY_CREATED_DATE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date activityCreatedDate;

    @Size(max = 50, message = "Updated by must be at most 50 characters")
    @Column(name = "ACTIVITY_UPDATED_BY", length = 50)
    private String activityUpdatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACTIVITY_UPDATED_DATE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date activityUpdatedDate;

    @Transient
    private String durationBetweenLeadAndLogTime;

    // Event-specific fields
    @Column(name = "EVENT_START_TIME")
    private Time eventStartTime;

    @Column(name = "EVENT_END_TIME")
    private Time eventEndTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "EVENT_END_DATE")
    private Date eventEndDate;

    @Column(name = "EVENT_LOCATION")
    private String eventLocation;

    @Column(name = "EVENT_ORGANIZER")
    private String eventOrganizer;

    @Column(name = "EVENT_PRIORITY")
    private String eventPriority;

    @Column(name = "REMINDER_SENT")
    private boolean reminderSent = true;

    // Default constructor
    public ActivityModel() {}

    // Getters and Setters for all fields
    public Integer getActivitySeqNo() {
        return activitySeqNo;
    }

    public void setActivitySeqNo(Integer activitySeqNo) {
        this.activitySeqNo = activitySeqNo;
    }

    public String getActivityRefId() {
        return activityRefId;
    }

    public void setActivityRefId(String activityRefId) {
        this.activityRefId = activityRefId;
    }

    public EnquiryModel getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(EnquiryModel enquiry) {
        this.enquiry = enquiry;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivitySubject() {
        return activitySubject;
    }

    public void setActivitySubject(String activitySubject) {
        this.activitySubject = activitySubject;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public Integer getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(Integer activityDuration) {
        this.activityDuration = activityDuration;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(String loggedTime) {
        this.loggedTime = loggedTime;
    }

    public String getOriginalEstimate() {
        return originalEstimate;
    }

    public void setOriginalEstimate(String originalEstimate) {
        this.originalEstimate = originalEstimate;
    }

    public Integer getProbabilityPercentage() {
        return probabilityPercentage;
    }

    public void setProbabilityPercentage(Integer probabilityPercentage) {
        this.probabilityPercentage = probabilityPercentage;
    }

    public String getActivityCreatedBy() {
        return activityCreatedBy;
    }

    public void setActivityCreatedBy(String activityCreatedBy) {
        this.activityCreatedBy = activityCreatedBy;
    }

    public Date getActivityCreatedDate() {
        return activityCreatedDate;
    }

    public void setActivityCreatedDate(Date activityCreatedDate) {
        this.activityCreatedDate = activityCreatedDate;
    }

    public String getActivityUpdatedBy() {
        return activityUpdatedBy;
    }

    public void setActivityUpdatedBy(String activityUpdatedBy) {
        this.activityUpdatedBy = activityUpdatedBy;
    }

    public Date getActivityUpdatedDate() {
        return activityUpdatedDate;
    }

    public void setActivityUpdatedDate(Date activityUpdatedDate) {
        this.activityUpdatedDate = activityUpdatedDate;
    }

    public String getDurationBetweenLeadAndLogTime() {
        return durationBetweenLeadAndLogTime;
    }

    public void setDurationBetweenLeadAndLogTime(String durationBetweenLeadAndLogTime) {
        this.durationBetweenLeadAndLogTime = durationBetweenLeadAndLogTime;
    }

    public Time getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Time eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public Time getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Time eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public Date getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventOrganizer() {
        return eventOrganizer;
    }

    public void setEventOrganizer(String eventOrganizer) {
        this.eventOrganizer = eventOrganizer;
    }

    public String getEventPriority() {
        return eventPriority;
    }

    public void setEventPriority(String eventPriority) {
        this.eventPriority = eventPriority;
    }

    public boolean isReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(boolean reminderSent) {
        this.reminderSent = reminderSent;
    }
}