package com.example.tarecruitment.model;

import java.util.ArrayList;
import java.util.List;

public class AiRecommendation {
    private String targetId;
    private String title;
    private int score;
    private List<String> reasons;
    private String note;

    public AiRecommendation() {
        this.reasons = new ArrayList<>();
    }

    public AiRecommendation(String targetId, String title, int score, List<String> reasons, String note) {
        this.targetId = targetId;
        this.title = title;
        this.score = score;
        this.reasons = reasons == null ? new ArrayList<>() : new ArrayList<>(reasons);
        this.note = note;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getReasons() {
        return reasons == null ? new ArrayList<>() : new ArrayList<>(reasons);
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons == null ? new ArrayList<>() : new ArrayList<>(reasons);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
