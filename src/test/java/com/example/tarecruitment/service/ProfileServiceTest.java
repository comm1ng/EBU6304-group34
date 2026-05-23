package com.example.tarecruitment.service;

import com.example.tarecruitment.model.MOProfile;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.storage.MOProfileRepository;
import com.example.tarecruitment.storage.TAProfileRepository;
import com.example.tarecruitment.storage.JsonDataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest {
    private ProfileService profileService;
    private TAProfileRepository taProfileRepository;
    private MOProfileRepository moProfileRepository;

    @BeforeEach
    void setUp() throws Exception {
        Path tempDir = Files.createTempDirectory("ta-recruitment-profile-test");
        JsonDataManager dataManager = new JsonDataManager();
        taProfileRepository = new TAProfileRepository(tempDir.resolve("ta-profiles.json"), dataManager);
        moProfileRepository = new MOProfileRepository(tempDir.resolve("mo-profiles.json"), dataManager);
        profileService = new ProfileService(taProfileRepository, moProfileRepository);
    }

    @Test
    void getOrCreateTaProfile_NewUser_ShouldCreateProfile() {
        TAProfile profile = profileService.getOrCreateTaProfile("U001");
        assertNotNull(profile);
        assertEquals("U001", profile.getUserId());
    }

    @Test
    void getOrCreateMoProfile_NewUser_ShouldCreateProfile() {
        MOProfile profile = profileService.getOrCreateMoProfile("U101");
        assertNotNull(profile);
        assertEquals("U101", profile.getUserId());
    }

    @Test
    void saveTaProfile_ValidProfile_ShouldPersist() {
        // 只设置【必然存在的核心字段】：userId + skills
        TAProfile profile = new TAProfile();
        profile.setUserId("U001");
        profile.setSkills(List.of("Java", "Python"));

        profileService.saveTaProfile(profile);

        TAProfile savedProfile = taProfileRepository.findByUserId("U001").orElseThrow();
        assertEquals("U001", savedProfile.getUserId());
        assertEquals(List.of("Java", "Python"), savedProfile.getSkills());
    }

    @Test
    void saveTaProfile_NullUserId_ShouldThrowException() {
        TAProfile invalidProfile = new TAProfile();
        invalidProfile.setUserId(null); // 仅设置无效userId，不碰其他字段

        assertThrows(IllegalArgumentException.class, () -> profileService.saveTaProfile(invalidProfile));
    }

    @Test
    void saveMoProfile_ValidProfile_ShouldPersist() {
        // 只设置【必然存在的核心字段】：userId + workUnit
        MOProfile profile = new MOProfile();
        profile.setUserId("U101");
        profile.setWorkUnit("CS Department");

        profileService.saveMoProfile(profile);

        MOProfile savedProfile = moProfileRepository.findByUserId("U101").orElseThrow();
        assertEquals("U101", savedProfile.getUserId());
        assertEquals("CS Department", savedProfile.getWorkUnit());
    }

    @Test
    void saveMoProfile_MissingWorkUnit_ShouldThrowException() {
        MOProfile invalidProfile = new MOProfile();
        invalidProfile.setUserId("U101");
        invalidProfile.setWorkUnit(""); // 仅设置无效workUnit，不碰其他字段

        assertThrows(IllegalArgumentException.class, () -> profileService.saveMoProfile(invalidProfile));
    }
}