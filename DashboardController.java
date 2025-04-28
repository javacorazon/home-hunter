package com.homehunter.controller;

import com.homehunter.model.ActivityLog;
import com.homehunter.service.ActivityLogService;
import com.homehunter.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final ActivityLogService activityLogService;
    private final NotificationService notificationService;

    public DashboardController(ActivityLogService activityLogService,
                               NotificationService notificationService) {
        this.activityLogService = activityLogService;
        this.notificationService = notificationService;
    }

    @GetMapping("/activity")
    public ResponseEntity<List<ActivityLog>> getUserActivity(@AuthenticationPrincipal Long userId) {
        List<ActivityLog> activities = activityLogService.getUserActivityLogs(userId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<String>> getNotifications(@AuthenticationPrincipal Long userId) {
        List<String> notifications = notificationService.getPersonalizedNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
}