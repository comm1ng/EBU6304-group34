package com.example.tarecruitment.model;

public class SystemStats {
    private int totalUsers;
    private int totalTaUsers;
    private int totalMoUsers;
    private int totalAdminUsers;
    private int totalJobs;
    private int totalApplications;

    public SystemStats() {
    }

    public SystemStats(int totalUsers, int totalTaUsers, int totalMoUsers, int totalAdminUsers, int totalJobs, int totalApplications) {
        this.totalUsers = totalUsers;
        this.totalTaUsers = totalTaUsers;
        this.totalMoUsers = totalMoUsers;
        this.totalAdminUsers = totalAdminUsers;
        this.totalJobs = totalJobs;
        this.totalApplications = totalApplications;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getTotalTaUsers() {
        return totalTaUsers;
    }

    public void setTotalTaUsers(int totalTaUsers) {
        this.totalTaUsers = totalTaUsers;
    }

    public int getTotalMoUsers() {
        return totalMoUsers;
    }

    public void setTotalMoUsers(int totalMoUsers) {
        this.totalMoUsers = totalMoUsers;
    }

    public int getTotalAdminUsers() {
        return totalAdminUsers;
    }

    public void setTotalAdminUsers(int totalAdminUsers) {
        this.totalAdminUsers = totalAdminUsers;
    }

    public int getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(int totalJobs) {
        this.totalJobs = totalJobs;
    }

    public int getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(int totalApplications) {
        this.totalApplications = totalApplications;
    }
}
