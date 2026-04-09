package com.group34.entity;

public class Application {
    private String appId;
    private String taId;
    private String jobId;
    private String status;

    public Application() {}

    public Application(String appId, String taId, String jobId, String status) {
        this.appId = appId;
        this.taId = taId;
        this.jobId = jobId;
        this.status = status;
    }

    // Getter & Setter
    public String getAppId() { return appId; }
    public void setAppId(String appId) { this.appId = appId; }
    public String getTaId() { return taId; }
    public void setTaId(String taId) { this.taId = taId; }
    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
