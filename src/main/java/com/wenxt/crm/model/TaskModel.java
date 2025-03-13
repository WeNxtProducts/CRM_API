package com.wenxt.crm.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "WN_TASK")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "task_due_date")
    private Date taskDueDate;

    @Column(name = "task_status")
    private String taskStatus; // e.g., Pending, In Progress, Completed

    @Column(name = "assigned_to")
    private String assignedTo; // e.g., User ID or Name

    @Column(name = "task_priority")
    private String taskPriority; // e.g., High, Medium, Low

    public TaskModel() {
    	
    }
    
    public TaskModel(Long id, String taskName, String taskDescription, Date taskDueDate, String taskStatus,
			String assignedTo, String taskPriority) {
		super();
		this.id = id;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskDueDate = taskDueDate;
		this.taskStatus = taskStatus;
		this.assignedTo = assignedTo;
		this.taskPriority = taskPriority;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Date getTaskDueDate() {
		return taskDueDate;
	}

	public void setTaskDueDate(Date taskDueDate) {
		this.taskDueDate = taskDueDate;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

        
}