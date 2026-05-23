package com.example.tarecruitment.service;

public interface AiChatClient {
    boolean isAvailable();

    String complete(String systemPrompt, String userPrompt);
}
