package com.homehunter.service;

import com.homehunter.model.ActivityLog;
import com.homehunter.model.User;
import com.homehunter.repository.ActivityLogRepository;
import com.homehunter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityLogService {
    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;

    public ActivityLogService(ActivityLogRepository activityLogRepository,
                              UserRepository userRepository) {
        this.activityLogRepository = activityLogRepository;
        this.userRepository = userRepository;
    }

    public void logActivity(Long userId, String activityType, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setActivityType(activityType);
        log.setDescription(description);
        log.setTimestamp(new Date());

        activityLogRepository.save(log);
    }

    public List<ActivityLog> getUserActivityLogs(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return activityLogRepository.findByUserOrderByTimestampDesc(user);
    }
}