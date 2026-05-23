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

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {
        Path tempDir = Files.createTempDirectory("ta-recruitment-user-test");
        JsonDataManager dataManager = new JsonDataManager();
        userRepository = new UserRepository(tempDir.resolve("users-user.json"), dataManager);
        userService = new UserService(userRepository);

        // 初始化测试用户
        User taUser = new User("U001", "ta1", "123456", "TA User", "ta1@bupt.edu.cn", List.of(Role.TA), List.of());
        User moUser = new User("U101", "mo1", "123456", "MO User", "mo1@bupt.edu.cn", List.of(Role.MO), List.of());
        User adminUser = new User("U999", "admin1", "123456", "Admin User", "admin1@bupt.edu.cn", List.of(Role.ADMIN), List.of());
        userRepository.saveAll(List.of(taUser, moUser, adminUser));
    }

    @Test
    void findById_ExistingUser_ShouldReturnUser() {
        Optional<User> user = userService.findById("U001");
        assertTrue(user.isPresent());
        assertEquals("ta1", user.get().getUsername());
    }

    @Test
    void findById_NonExistingUser_ShouldReturnEmpty() {
        Optional<User> user = userService.findById("U9999");
        assertFalse(user.isPresent());
    }

    @Test
    void findByEmail_ExistingEmail_ShouldReturnUser() {
        Optional<User> user = userService.findByEmail("mo1@bupt.edu.cn");
        assertTrue(user.isPresent());
        assertEquals("U101", user.get().getId());
    }

    @Test
    void getTAUsers_ShouldReturnOnlyTA() {
        List<User> taUsers = userService.getTAUsers();
        assertEquals(1, taUsers.size());
        assertEquals("U001", taUsers.get(0).getId());
    }

    @Test
    void getMOUsers_ShouldReturnOnlyMO() {
        List<User> moUsers = userService.getMOUsers();
        assertEquals(1, moUsers.size());
        assertEquals("U101", moUsers.get(0).getId());
    }

    @Test
    void getAdminUsers_ShouldReturnOnlyAdmin() {
        List<User> adminUsers = userService.getAdminUsers();
        assertEquals(1, adminUsers.size());
        assertEquals("U999", adminUsers.get(0).getId());
    }

    @Test
    void updateBasicInfo_ValidUser_ShouldUpdateNameAndEmail() {
        userService.updateBasicInfo("U001", "Updated TA Name", "updated_ta1@bupt.edu.cn");
        User updatedUser = userRepository.findById("U001").orElseThrow();
        assertEquals("Updated TA Name", updatedUser.getFullName());
        assertEquals("updated_ta1@bupt.edu.cn", updatedUser.getEmail());
    }

    @Test
    void updateBasicInfo_InvalidEmail_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.updateBasicInfo("U001", "Updated Name", "invalid-email")
        );
    }

    @Test
    void updateBasicInfo_NonExistingUser_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.updateBasicInfo("U9999", "Non Existing", "non-existing@bupt.edu.cn")
        );
    }
}