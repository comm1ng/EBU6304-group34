package com.example.tarecruitment.service;

import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.storage.UserRepository;
import com.example.tarecruitment.util.ValidationUtil;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getTAUsers() {
        return userRepository.findByRole(Role.TA.name());
    }

    public List<User> getMOUsers() {
        return userRepository.findByRole(Role.MO.name());
    }

    public List<User> getAdminUsers() {
        return userRepository.findByRole(Role.ADMIN.name());
    }

    public void updateBasicInfo(String userId, String fullName, String email) {
        ValidationUtil.requireNotBlank(fullName, "Full name");
        ValidationUtil.requireEmail(email);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        user.setFullName(fullName.trim());
        user.setEmail(email.trim());
        userRepository.update(user);
    }
}
