package com.example.tarecruitment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class User {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private List<Role> roles;
    private List<String> skills;

    public User() {
        this.roles = new ArrayList<>();
        this.skills = new ArrayList<>();
    }

    public User(String id, String username, String password, String fullName, String email, List<Role> roles, List<String> skills) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.roles = roles == null ? new ArrayList<>() : new ArrayList<>(roles);
        this.skills = skills == null ? new ArrayList<>() : new ArrayList<>(skills);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return new ArrayList<>(roles);
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles == null
                ? new ArrayList<>()
                : roles.stream().filter(Objects::nonNull).distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    public void addRole(Role role) {
        if (role == null) {
            return;
        }
        if (roles == null) {
            roles = new ArrayList<>();
        }
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    public boolean hasRole(Role role) {
        return role != null && getRoles().contains(role);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getSkills() {
        return skills == null ? new ArrayList<>() : new ArrayList<>(skills);
    }

    public void setSkills(List<String> skills) {
        this.skills = skills == null ? new ArrayList<>() : new ArrayList<>(skills);
    }
}
