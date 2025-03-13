package com.wenxt.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "WN_ACTIVITY_LOG")
public class ActivityLogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_type")
    private String activityType; // e.g., Enquiry Generated, Quote Converted

    @Column(name = "activity_description")
    private String activityDescription;

    @Column(name = "activity_date")
    private Date activityDate;

    //@Column(name = "related_lead_id")
    //private Long relatedLeadId; 
    // Foreign key to Lead

    @Column(name = "activity_status")
    private String activityStatus; // e.g., Pending, Completed

    public ActivityLogModel() {
    	
    }

    public ActivityLogModel(Long id, String activityType, String activityDescription, Date activityDate,
			String activityStatus) {
		super();
		this.id = id;
		this.activityType = activityType;
		this.activityDescription = activityDescription;
		this.activityDate = activityDate;
		this.activityStatus = activityStatus;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
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

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

    
    
}