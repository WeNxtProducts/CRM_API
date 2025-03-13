// ActivityController.java
package com.wenxt.crm.controller;

import com.wenxt.crm.model.ActivityModel;
import com.wenxt.crm.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityModel> createActivity(@RequestBody ActivityModel activity) {
        return ResponseEntity.ok(activityService.createActivity(activity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityModel> updateActivity(
            @PathVariable Integer id, 
            @RequestBody ActivityModel activity) {
        return ResponseEntity.ok(activityService.updateActivity(id, activity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityModel> getActivityById(@PathVariable Integer id) {
        Optional<ActivityModel> activity = activityService.getActivityById(id);
        return activity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ActivityModel>> getActivities(
            @RequestParam(required = false) String activityType) {
        return ResponseEntity.ok(activityService.getAllActivities(activityType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Integer id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}