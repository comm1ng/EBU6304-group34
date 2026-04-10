package com.example.tarecruitment.storage;

import com.example.tarecruitment.model.User;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository {
    private final Path path;
    private final JsonDataManager dataManager;

    public UserRepository(Path path, JsonDataManager dataManager) {
        this.path = path;
        this.dataManager = dataManager;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>(dataManager.readList(path, User.class));
        for (User user : users) {
            user.setRoles(user.getRoles());
        }
        return users;
    }

    public Optional<User> findById(String id) {
        return findAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByUsername(String username) {
        if (username == null || username.isBlank()) {
            return Optional.empty();
        }
        return findAll().stream()
                .filter(u -> u.getUsername() != null && u.getUsername().equalsIgnoreCase(username.trim()))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }

    public Optional<User> findByLoginIdentifier(String identifier) {
        if (identifier == null || identifier.isBlank()) {
            return Optional.empty();
        }
        String normalized = identifier.trim();
        return findAll().stream()
                .filter(u -> (u.getUsername() != null && u.getUsername().equalsIgnoreCase(normalized))
                        || (u.getEmail() != null && u.getEmail().equalsIgnoreCase(normalized)))
                .findFirst();
    }

    public List<User> findByRole(String roleName) {
        if (roleName == null || roleName.isBlank()) {
            return new ArrayList<>();
        }
        String normalized = roleName.trim().toUpperCase();
        return findAll().stream()
                .filter(u -> u.getRoles().stream().anyMatch(r -> r.name().equals(normalized)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void saveAll(List<User> users) {
        dataManager.writeList(path, users);
    }

    public void add(User user) {
        List<User> users = findAll();
        users.add(user);
        saveAll(users);
    }

    public void update(User updatedUser) {
        List<User> users = findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                saveAll(users);
                return;
            }
        }
        throw new IllegalArgumentException("User not found: " + updatedUser.getId());
    }
}
