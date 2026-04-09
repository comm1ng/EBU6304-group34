package com.example.tarecruitment.model;

import java.util.ArrayList;
import java.util.List;

public class TAProfile {
    private String userId;
    private String major;
    private String academicYear;
    private List<String> skills;
    private String cvFilePath;
    private String cvSummary;
    private String experience;

    public TAProfile() {
        this.skills = new ArrayList<>();
    }

    public TAProfile(String userId, String major, String academicYear, List<String> skills, String cvFilePath, String cvSummary, String experience) {
        this.userId = userId;
        this.major = major;
        this.academicYear = academicYear;
        this.skills = skills == null ? new ArrayList<>() : new ArrayList<>(skills);
        this.cvFilePath = cvFilePath;
        this.cvSummary = cvSummary;
        this.experience = experience;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public List<String> getSkills() {
        return skills == null ? new ArrayList<>() : new ArrayList<>(skills);
    }

    public void setSkills(List<String> skills) {
        this.skills = skills == null ? new ArrayList<>() : new ArrayList<>(skills);
    }

    public String getCvFilePath() {
        return cvFilePath;
    }

    public void setCvFilePath(String cvFilePath) {
        this.cvFilePath = cvFilePath;
    }

    public String getCvSummary() {
        return cvSummary;
    }

    public void setCvSummary(String cvSummary) {
        this.cvSummary = cvSummary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
