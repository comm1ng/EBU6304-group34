package com.example.tarecruitment.model;

public class MOProfile {
    private String userId;
    private String workUnit;
    private String title;
    private String bio;

    public MOProfile() {
    }

    public MOProfile(String userId, String workUnit, String title, String bio) {
        this.userId = userId;
        this.workUnit = workUnit;
        this.title = title;
        this.bio = bio;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
