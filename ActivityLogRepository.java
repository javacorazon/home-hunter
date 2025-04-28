package com.homehunter.repository;

import com.homehunter.model.ActivityLog;
import com.homehunter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByUserOrderByTimestampDesc(User user);
}