package com.example.tarecruitment.service;

import com.example.tarecruitment.model.User;
import com.example.tarecruitment.storage.UserRepository;

import java.util.Optional;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> login(String loginIdentifier, String password) {
        if (loginIdentifier == null || loginIdentifier.isBlank() || password == null) {
            return Optional.empty();
        }
        return userRepository.findByLoginIdentifier(loginIdentifier.trim())
                .filter(user -> user.getPassword().equals(password));
    }
}
