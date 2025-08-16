package com.example.first_springboot_1.dao;

import com.example.first_springboot_1.entities.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> { }
