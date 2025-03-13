package com.wenxt.crm.util;

import com.wenxt.crm.model.ActivityLogModel;
import com.wenxt.crm.repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RemainderUtility {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    @SuppressWarnings("deprecation")
    public void sendRemainderAndLog(String activityType, String description, Date remainderTime) {
        // Log the activity
        ActivityLogModel log = new ActivityLogModel();
        log.setActivityDate(new Date());
        log.setActivityType(activityType);
        log.setActivityDescription(description);
        activityLogRepository.save(log);

        // Schedule the remainder (if applicable)
        if (remainderTime != null && remainderTime.after(new Date())) {
            taskScheduler.schedule(() -> {
                // Implement your remainder logic (e.g., send email, notification, etc.)
                System.out.println("Remainder: " + description);

                // Log that the reminder was sent
                ActivityLogModel remainderLog = new ActivityLogModel();
                remainderLog.setActivityDate(new Date());
                remainderLog.setActivityType("REMAINDER_SENT");
                remainderLog.setActivityDescription("Remainder sent: " + description);
                activityLogRepository.save(remainderLog);
            }, remainderTime);
        }
    }
}