package com.example.first_springboot_1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"enrollment_id"}))
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private Enrollment enrollment;

    @Min(0) @Max(100)
    private int score;

    private String letter;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; this.letter = toLetter(score); }
    public String getLetter() { return letter; }
    private String toLetter(int s) {
        if (s >= 90) return "A";
        if (s >= 80) return "B";
        if (s >= 70) return "C";
        if (s >= 60) return "D";
        return "F";
    }
}
