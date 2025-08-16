package com.example.first_springboot_1.entities;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant timestamp = Instant.now();
    private String actor; // username or id
    private String action; // e.g., ENROLL, CREATE_COURSE
    private String details;
    private String level; // INFO, WARN, ERROR

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}
