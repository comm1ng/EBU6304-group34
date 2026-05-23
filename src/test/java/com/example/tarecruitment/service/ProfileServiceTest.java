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
        assertEquals("U001", profile.getUserId());
        assertEquals("", profile.getBio());
        assertEquals(List.of(), profile.getSkills());
    }

    @Test
    void getOrCreateMoProfile_NewUser_ShouldCreateProfile() {
        MOProfile profile = profileService.getOrCreateMoProfile("U101");
        assertEquals("U101", profile.getUserId());
        assertEquals("", profile.getWorkUnit());
    }

    @Test
    void saveTaProfile_ValidProfile_ShouldPersist() {
        TAProfile profile = new TAProfile("U001", "Java Expert", "Beijing", List.of("Java", "Python"), "Undergraduate", "CS", "3 years");
        profileService.saveTaProfile(profile);

        TAProfile savedProfile = taProfileRepository.findByUserId("U001").orElseThrow();
        assertEquals("Java Expert", savedProfile.getBio());
        assertEquals(List.of("Java", "Python"), savedProfile.getSkills());
    }

    @Test
    void saveTaProfile_NullUserId_ShouldThrowException() {
        TAProfile invalidProfile = new TAProfile(null, "Bio", "", List.of(), "", "", "");
        assertThrows(IllegalArgumentException.class, () -> profileService.saveTaProfile(invalidProfile));
    }

    @Test
    void saveMoProfile_ValidProfile_ShouldPersist() {
        MOProfile profile = new MOProfile("U101", "Dr. Li", "CS Department", "Beijing University of Posts and Telecommunications");
        profileService.saveMoProfile(profile);

        MOProfile savedProfile = moProfileRepository.findByUserId("U101").orElseThrow();
        assertEquals("CS Department", savedProfile.getWorkUnit());
        assertEquals("Dr. Li", savedProfile.getFullName());
    }

    @Test
    void saveMoProfile_MissingWorkUnit_ShouldThrowException() {
        MOProfile invalidProfile = new MOProfile("U101", "Dr. Li", "", "");
        assertThrows(IllegalArgumentException.class, () -> profileService.saveMoProfile(invalidProfile));
    }
}