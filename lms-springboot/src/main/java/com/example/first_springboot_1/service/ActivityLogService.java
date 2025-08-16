package com.example.first_springboot_1.service;

import com.example.first_springboot_1.dao.ActivityLogRepository;
import com.example.first_springboot_1.entities.ActivityLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityLogService {
    private static final Logger log = LoggerFactory.getLogger(ActivityLogService.class);
    private final ActivityLogRepository repo;

    public ActivityLogService(ActivityLogRepository repo) { this.repo = repo; }

    @Transactional
    public ActivityLog log(String actor, String action, String details, String level) {
        ActivityLog entry = new ActivityLog();
        entry.setActor(actor);
        entry.setAction(action);
        entry.setDetails(details);
        entry.setLevel(level);
        log.info("ACTIVITY {} by {} - {}", action, actor, details);
        return repo.save(entry);
    }
}
