package com.example.tarecruitment.service;

import com.example.tarecruitment.model.MOProfile;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.storage.MOProfileRepository;
import com.example.tarecruitment.storage.TAProfileRepository;
import com.example.tarecruitment.util.ValidationUtil;

import java.util.List;

public class ProfileService {
    private final TAProfileRepository taProfileRepository;
    private final MOProfileRepository moProfileRepository;

    public ProfileService(TAProfileRepository taProfileRepository, MOProfileRepository moProfileRepository) {
        this.taProfileRepository = taProfileRepository;
        this.moProfileRepository = moProfileRepository;
    }

    public TAProfile getOrCreateTaProfile(String userId) {
        return taProfileRepository.findByUserId(userId)
                .orElseGet(() -> new TAProfile(userId, "", "", List.of(), "", "", ""));
    }

    public MOProfile getOrCreateMoProfile(String userId) {
        return moProfileRepository.findByUserId(userId)
                .orElseGet(() -> new MOProfile(userId, "", "", ""));
    }

    public void saveTaProfile(TAProfile profile) {
        if (profile == null || profile.getUserId() == null || profile.getUserId().isBlank()) {
            throw new IllegalArgumentException("Profile user ID is required.");
        }
        taProfileRepository.upsert(profile);
    }

    public void saveMoProfile(MOProfile profile) {
        if (profile == null || profile.getUserId() == null || profile.getUserId().isBlank()) {
            throw new IllegalArgumentException("Profile user ID is required.");
        }
        ValidationUtil.requireNotBlank(profile.getWorkUnit(), "Work unit / organisation");
        moProfileRepository.upsert(profile);
    }
}
