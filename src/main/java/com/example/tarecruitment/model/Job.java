package com.example.tarecruitment.model;

import java.util.ArrayList;
import java.util.List;

public class Job {
    private String id;
    private String title;
    private String description;
    private List<String> requiredSkills;
    private int hoursPerWeek;
    private String deadline;
    private String locationMode;
    private String postedByUserId;
    private JobStatus status;
    private String createdAt;

    public Job() {
        this.requiredSkills = new ArrayList<>();
    }

    public Job(String id, String title, String description, List<String> requiredSkills, int hoursPerWeek,
               String postedByUserId, JobStatus status, String createdAt) {
        this(id, title, description, requiredSkills, hoursPerWeek, "", "", postedByUserId, status, createdAt);
    }

    public Job(String id, String title, String description, List<String> requiredSkills, int hoursPerWeek,
               String deadline, String locationMode, String postedByUserId, JobStatus status, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.requiredSkills = requiredSkills == null ? new ArrayList<>() : new ArrayList<>(requiredSkills);
        this.hoursPerWeek = hoursPerWeek;
        this.deadline = deadline;
        this.locationMode = locationMode;
        this.postedByUserId = postedByUserId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills == null ? new ArrayList<>() : new ArrayList<>(requiredSkills);
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills == null ? new ArrayList<>() : new ArrayList<>(requiredSkills);
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getLocationMode() {
        return locationMode;
    }

    public void setLocationMode(String locationMode) {
        this.locationMode = locationMode;
    }

    public String getPostedByUserId() {
        return postedByUserId;
    }

    public void setPostedByUserId(String postedByUserId) {
        this.postedByUserId = postedByUserId;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return id + " - " + title;
    }
}
