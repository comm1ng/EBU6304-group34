package com.example.tarecruitment.model;

public class WorkloadSummary {
    private String taUserId;
    private String taName;
    private int acceptedJobs;
    private int totalHoursPerWeek;
    private int pendingApplications;

    public WorkloadSummary() {
    }

    public WorkloadSummary(String taUserId, String taName, int acceptedJobs, int totalHoursPerWeek, int pendingApplications) {
        this.taUserId = taUserId;
        this.taName = taName;
        this.acceptedJobs = acceptedJobs;
        this.totalHoursPerWeek = totalHoursPerWeek;
        this.pendingApplications = pendingApplications;
    }

    public String getTaUserId() {
        return taUserId;
    }

    public void setTaUserId(String taUserId) {
        this.taUserId = taUserId;
    }

    public String getTaName() {
        return taName;
    }

    public void setTaName(String taName) {
        this.taName = taName;
    }

    public int getAcceptedJobs() {
        return acceptedJobs;
    }

    public void setAcceptedJobs(int acceptedJobs) {
        this.acceptedJobs = acceptedJobs;
    }

    public int getTotalHoursPerWeek() {
        return totalHoursPerWeek;
    }

    public void setTotalHoursPerWeek(int totalHoursPerWeek) {
        this.totalHoursPerWeek = totalHoursPerWeek;
    }

    public int getPendingApplications() {
        return pendingApplications;
    }

    public void setPendingApplications(int pendingApplications) {
        this.pendingApplications = pendingApplications;
    }
}
