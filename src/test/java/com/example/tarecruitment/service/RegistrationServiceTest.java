package com.example.tarecruitment.service;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.storage.MOProfileRepository;
import com.example.tarecruitment.storage.TAProfileRepository;
import com.example.tarecruitment.storage.UserRepository;
import com.example.tarecruitment.storage.JsonDataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {
    private RegistrationService registrationService;
    private UserRepository userRepository;
    private TAProfileRepository taProfileRepository;
    private MOProfileRepository moProfileRepository;

    @BeforeEach
    void setUp() throws Exception {
        Path tempDir = Files.createTempDirectory("ta-recruitment-reg-test");
        JsonDataManager dataManager = new JsonDataManager();
        userRepository = new UserRepository(tempDir.resolve("users-reg.json"), dataManager);
        taProfileRepository = new TAProfileRepository(tempDir.resolve("ta-profiles-reg.json"), dataManager);
        moProfileRepository = new MOProfileRepository(tempDir.resolve("mo-profiles-reg.json"), dataManager);
        registrationService = new RegistrationService(userRepository, taProfileRepository, moProfileRepository);

        // 初始化已存在的用户
        User existingUser = new User("U001", "existing_ta", "123456", "Existing User", "existing@bupt.edu.cn", List.of(Role.TA), List.of());
        userRepository.add(existingUser);
    }

    @Test
    void register_TA_ShouldCreateUserAndProfile() {
        User newTA = registrationService.register(
                "new_ta",
                "654321",
                "New TA User",
                "newta@bupt.edu.cn",
                Role.TA,
                "",
                false
        );

        assertNotNull(newTA.getId());
        assertEquals("new_ta", newTA.getUsername());
        assertEquals(List.of(Role.TA), newTA.getRoles());
        assertTrue(taProfileRepository.findByUserId(newTA.getId()).isPresent());
    }

    @Test
    void register_MO_ShouldCreateUserAndProfile() {
        User newMO = registrationService.register(
                "new_mo",
                "654321",
                "New MO User",
                "newmo@bupt.edu.cn",
                Role.MO,
                "CS Department",
                false
        );

        assertNotNull(newMO.getId());
        assertEquals(List.of(Role.MO), newMO.getRoles());
        MOProfile moProfile = moProfileRepository.findByUserId(newMO.getId()).orElseThrow();
        assertEquals("CS Department", moProfile.getWorkUnit());
    }

    @Test
    void register_DuplicateUsername_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(
                        "existing_ta",
                        "654321",
                        "Duplicate User",
                        "duplicate@bupt.edu.cn",
                        Role.TA,
                        "",
                        false
                )
        );
    }

    @Test
    void register_DuplicateEmail_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(
                        "new_username",
                        "654321",
                        "Duplicate Email User",
                        "existing@bupt.edu.cn",
                        Role.TA,
                        "",
                        false
                )
        );
    }

    @Test
    void register_ShortPassword_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(
                        "short_pass",
                        "12345", // 长度不足6
                        "Short Pass User",
                        "shortpass@bupt.edu.cn",
                        Role.TA,
                        "",
                        false
                )
        );
    }

    @Test
    void register_MO_MissingWorkUnit_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(
                        "mo_no_workunit",
                        "654321",
                        "MO No Workunit",
                        "monowork@bupt.edu.cn",
                        Role.MO,
                        "",
                        false
                )
        );
    }

    @Test
    void register_InvalidRole_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                registrationService.register(
                        "admin_try",
                        "654321",
                        "Admin Try",
                        "admintry@bupt.edu.cn",
                        Role.ADMIN,
                        "",
                        false
                )
        );
    }
}