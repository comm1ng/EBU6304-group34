package com.example.tarecruitment.service;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.storage.UserRepository;
import com.example.tarecruitment.storage.JsonDataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {
    private AuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {
        Path tempDir = Files.createTempDirectory("ta-recruitment-auth-test");
        JsonDataManager dataManager = new JsonDataManager();
        userRepository = new UserRepository(tempDir.resolve("users-auth.json"), dataManager);
        authService = new AuthService(userRepository);

        // 初始化测试用户
        User testUser = new User("U001", "test_ta", "123456", "Test User", "test@bupt.edu.cn", List.of(Role.TA), List.of());
        userRepository.add(testUser);
    }

    @Test
    void login_ValidCredentials_ShouldReturnUser() {
        Optional<User> user = authService.login("test_ta", "123456");
        assertTrue(user.isPresent());
        assertEquals("U001", user.get().getId());
        assertEquals("test_ta", user.get().getUsername());
    }

    @Test
    void login_InvalidPassword_ShouldReturnEmpty() {
        Optional<User> user = authService.login("test_ta", "wrong_pass");
        assertFalse(user.isPresent());
    }

    @Test
    void login_BlankUsername_ShouldReturnEmpty() {
        Optional<User> user = authService.login("", "123456");
        assertFalse(user.isPresent());
    }

    @Test
    void login_NullPassword_ShouldReturnEmpty() {
        Optional<User> user = authService.login("test_ta", null);
        assertFalse(user.isPresent());
    }

    @Test
    void login_TrimmedUsername_ShouldMatch() {
        Optional<User> user = authService.login("  test_ta  ", "123456");
        assertTrue(user.isPresent());
    }
}