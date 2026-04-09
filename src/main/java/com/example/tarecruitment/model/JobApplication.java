package com.example.tarecruitment.model;

public class JobApplication {
    private String id;
    private String jobId;
    private String taUserId;
    private ApplicationStatus status;
    private String appliedAt;
    private String reviewedAt;
    private String reviewedByUserId;

    public JobApplication() {
    }

    public JobApplication(String id, String jobId, String taUserId, ApplicationStatus status, String appliedAt, String reviewedAt, String reviewedByUserId) {
        this.id = id;
        this.jobId = jobId;
        this.taUserId = taUserId;
        this.status = status;
        this.appliedAt = appliedAt;
        this.reviewedAt = reviewedAt;
        this.reviewedByUserId = reviewedByUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTaUserId() {
        return taUserId;
    }

    public void setTaUserId(String taUserId) {
        this.taUserId = taUserId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(String appliedAt) {
        this.appliedAt = appliedAt;
    }

    public String getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(String reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getReviewedByUserId() {
        return reviewedByUserId;
    }

    public void setReviewedByUserId(String reviewedByUserId) {
        this.reviewedByUserId = reviewedByUserId;
    }
}
