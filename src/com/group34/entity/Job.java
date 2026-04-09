package com.group34.entity;

public class Job {
    private String jobId;
    private String title;
    private String content;
    private String moId;

    public Job() {}

    public Job(String jobId, String title, String content, String moId) {
        this.jobId = jobId;
        this.title = title;
        this.content = content;
        this.moId = moId;
    }

    // Getter & Setter
    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getMoId() { return moId; }
    public void setMoId(String moId) { this.moId = moId; }
}
