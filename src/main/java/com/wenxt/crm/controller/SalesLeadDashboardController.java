package com.wenxt.crm.controller;

//import com.wenxt.crm.model.ActivityModel;
import com.wenxt.crm.model.ActivityLogModel;
import com.wenxt.crm.model.TaskModel;
import com.wenxt.crm.service.SalesLeadDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/saleslead/dashboard")
public class SalesLeadDashboardController {

    @Autowired
    private SalesLeadDashboardService salesLeadDashboardService;

    // GET method for dashboard statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        return salesLeadDashboardService.getDashboardStats();
    }   

    // GET method for activity logs
    @GetMapping("/activitylogs")
    public ResponseEntity<Map<String, Object>> getActivityLog() {
        return salesLeadDashboardService.getActivityLog();
    }

    // GET method for tasks
    @GetMapping("/tasks")
    public ResponseEntity<Map<String, Object>> getTasks() {
        return salesLeadDashboardService.getTasks();
    }

    // GET method for calendar data
    @GetMapping("/calendar")
    public ResponseEntity<Map<String, Object>> getCalendarData(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return salesLeadDashboardService.getCalendarData(startDate, endDate);
    }

    // GET method for sales graph data
    @GetMapping("/salesgraph")
    public ResponseEntity<Map<String, Object>> getSalesGraphData() {
        return salesLeadDashboardService.getSalesGraphData();
    }

    // POST method to create a new activity log
    @PostMapping("/activitylogs")
    public ResponseEntity<Map<String, Object>> createActivityLog(@RequestBody ActivityLogModel activityLog) {
        return salesLeadDashboardService.createActivityLog(activityLog);
    }

    // POST method to create a new task
    @PostMapping("/tasks")
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody TaskModel task) {
        return salesLeadDashboardService.createTask(task);
    }

    // PUT method to update an activity log
    @PutMapping("/activitylogs/{id}")
    public ResponseEntity<Map<String, Object>> updateActivityLog(@PathVariable Long id, @RequestBody ActivityLogModel activityLog) {
        return salesLeadDashboardService.updateActivityLog(id, activityLog);
    }

    // PUT method to update a task
    @PutMapping("/tasks/{id}")
    public ResponseEntity<Map<String, Object>> updateTask(@PathVariable Long id, @RequestBody TaskModel task) {
        return salesLeadDashboardService.updateTask(id, task);
    } 

    // DELETE method to delete an activity log
    @DeleteMapping("/activitylogs/{id}")
    public ResponseEntity<Map<String, Object>> deleteActivityLog(@PathVariable Long id) {
        return salesLeadDashboardService.deleteActivityLog(id);
    }

    // DELETE method to delete a task
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Map<String, Object>> deleteTask(@PathVariable Long id) {
        return salesLeadDashboardService.deleteTask(id);
    }
    
    
//    // POST method to create a new activity (replaces separate event/appointment endpoints)
//    @PostMapping("/activities")
//    public ResponseEntity<Map<String, Object>> createActivity(@RequestBody ActivityModel activity) {
//        return salesLeadDashboardService.createActivity(activity);
//    }
//    // GET method for activities (replaces separate events endpoints)
//    @GetMapping("/activities")
//    public ResponseEntity<Map<String, Object>> getActivities(
//            @RequestParam(required = false) String activityType) {
//        return salesLeadDashboardService.getActivities(activityType);
//    }
//    
//    // DELETE method to delete an activity
//    @DeleteMapping("/activities/{id}")
//    public ResponseEntity<Map<String, Object>> deleteActivity(@PathVariable Long id) {
//        return salesLeadDashboardService.deleteActivity(id);
//    }
//    
//    // PUT method to update an activity
//    @PutMapping("/activities/{id}")
//    public ResponseEntity<Map<String, Object>> updateActivity(@PathVariable Long id, @RequestBody ActivityModel activity) {
//        return salesLeadDashboardService.updateActivity(id, activity);
//    }
}