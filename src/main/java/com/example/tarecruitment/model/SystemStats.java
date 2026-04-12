package com.example.tarecruitment.model;

public class SystemStats {
    private int totalUsers;
    private int totalTaUsers;
    private int totalMoUsers;
    private int totalAdminUsers;
    private int totalJobs;
    private int openJobs;
    private int totalApplications;
    private int acceptedApplications;

    public SystemStats() {
    }

    public SystemStats(int totalUsers, int totalTaUsers, int totalMoUsers, int totalAdminUsers, int totalJobs, int totalApplications) {
        this(totalUsers, totalTaUsers, totalMoUsers, totalAdminUsers, totalJobs, 0, totalApplications, 0);
    }

    public SystemStats(int totalUsers, int totalTaUsers, int totalMoUsers, int totalAdminUsers, int totalJobs, int openJobs, int totalApplications, int acceptedApplications) {
        this.totalUsers = totalUsers;
        this.totalTaUsers = totalTaUsers;
        this.totalMoUsers = totalMoUsers;
        this.totalAdminUsers = totalAdminUsers;
        this.totalJobs = totalJobs;
        this.openJobs = openJobs;
        this.totalApplications = totalApplications;
        this.acceptedApplications = acceptedApplications;
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

    public int getOpenJobs() {
        return openJobs;
    }

    public void setOpenJobs(int openJobs) {
        this.openJobs = openJobs;
    }

    public int getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(int totalApplications) {
        this.totalApplications = totalApplications;
    }

    public int getAcceptedApplications() {
        return acceptedApplications;
    }

    public void setAcceptedApplications(int acceptedApplications) {
        this.acceptedApplications = acceptedApplications;
    }
}
