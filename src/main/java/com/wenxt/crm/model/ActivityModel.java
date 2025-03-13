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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACTIVITY_START_DATE")
    private Date activityStartDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "ACTIVITY_END_DATE")
    private Date activityEndDate;

    @Column(name = "ACTIVITY_START_TIME")
    private Time activityStartTime;

    @Column(name = "ACTIVITY_END_TIME")
    private Time activityEndTime;

    @Column(name = "ACTIVITY_STATUS")
    private String activityStatus="Pending";
    
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
  
    @Column(name = "ACTIVITY_PRIORITY")
    private String activityPriority;

    @Column(name = "REMINDER_SENT")
    private boolean reminderSent = true;

    // Default constructor
    public ActivityModel() {}

	public ActivityModel(Integer activitySeqNo,
			@Size(max = 50, message = "Activity reference ID must be at most 50 characters") String activityRefId,
			EnquiryModel enquiry,
			@NotBlank(message = "Activity type is required") @Size(max = 50, message = "Activity type must be at most 50 characters") String activityType,
			@Size(max = 255, message = "Activity subject must be at most 255 characters") String activitySubject,
			String activityDescription, Date activityStartDate, Date activityEndDate, Time activityStartTime,
			Time activityEndTime, String activityStatus, Integer probabilityPercentage, String activityCreatedBy,
			Date activityCreatedDate,
			@Size(max = 50, message = "Updated by must be at most 50 characters") String activityUpdatedBy,
			Date activityUpdatedDate, String activityPriority, boolean reminderSent) {
		super();
		this.activitySeqNo = activitySeqNo;
		this.activityRefId = activityRefId;
		this.enquiry = enquiry;
		this.activityType = activityType;
		this.activitySubject = activitySubject;
		this.activityDescription = activityDescription;
		this.activityStartDate = activityStartDate;
		this.activityEndDate = activityEndDate;
		this.activityStartTime = activityStartTime;
		this.activityEndTime = activityEndTime;
		this.activityStatus = activityStatus;
		this.probabilityPercentage = probabilityPercentage;
		this.activityCreatedBy = activityCreatedBy;
		this.activityCreatedDate = activityCreatedDate;
		this.activityUpdatedBy = activityUpdatedBy;
		this.activityUpdatedDate = activityUpdatedDate;
		this.activityPriority = activityPriority;
		this.reminderSent = reminderSent;
	}



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

	public Date getActivityStartDate() {
		return activityStartDate;
	}

	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}

	public Date getActivityEndDate() {
		return activityEndDate;
	}

	public void setActivityEndDate(Date activityEndDate) {
		this.activityEndDate = activityEndDate;
	}

	public Time getActivityStartTime() {
		return activityStartTime;
	}

	public void setActivityStartTime(Time activityStartTime) {
		this.activityStartTime = activityStartTime;
	}

	public Time getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Time activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
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

	public String getActivityPriority() {
		return activityPriority;
	}

	public void setActivityPriority(String activityPriority) {
		this.activityPriority = activityPriority;
	}

	public boolean isReminderSent() {
		return reminderSent;
	}

	public void setReminderSent(boolean reminderSent) {
		this.reminderSent = reminderSent;
	}

    
    

}