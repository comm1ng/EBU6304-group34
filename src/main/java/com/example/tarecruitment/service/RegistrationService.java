package com.example.tarecruitment.service;

import com.example.tarecruitment.model.MOProfile;
import com.example.tarecruitment.model.Role;
import com.example.tarecruitment.model.TAProfile;
import com.example.tarecruitment.model.User;
import com.example.tarecruitment.storage.MOProfileRepository;
import com.example.tarecruitment.storage.TAProfileRepository;
import com.example.tarecruitment.storage.UserRepository;
import com.example.tarecruitment.util.IdGenerator;
import com.example.tarecruitment.util.ValidationUtil;

import java.util.List;
import java.util.stream.Collectors;

public class RegistrationService {
    private final UserRepository userRepository;
    private final TAProfileRepository taProfileRepository;
    private final MOProfileRepository moProfileRepository;

    public RegistrationService(UserRepository userRepository,
                               TAProfileRepository taProfileRepository,
                               MOProfileRepository moProfileRepository) {
        this.userRepository = userRepository;
        this.taProfileRepository = taProfileRepository;
        this.moProfileRepository = moProfileRepository;
    }

    public User register(String username,
                         String password,
                         String fullName,
                         String email,
                         Role requestedRole,
                         String workUnit,
                         boolean confirmCrossRole) {
        ValidationUtil.requireNotBlank(username, "Username");
        ValidationUtil.requireMinLength(password, 6, "Password");
        ValidationUtil.requireNotBlank(fullName, "Full name");
        ValidationUtil.requireEmail(email);

        if (requestedRole != Role.TA && requestedRole != Role.MO) {
            throw new IllegalArgumentException("Only TA and MO registration is allowed.");
        }
        if (requestedRole == Role.MO) {
            ValidationUtil.requireNotBlank(workUnit, "Work unit / organisation");
        }

        String normalizedUsername = username.trim();
        String normalizedEmail = email.trim();
        String normalizedFullName = fullName.trim();

        User sameUsername = userRepository.findByUsername(normalizedUsername).orElse(null);
        User sameEmail = userRepository.findByEmail(normalizedEmail).orElse(null);

        if (sameEmail == null && sameUsername != null) {
            throw new IllegalArgumentException("Username already exists. Please choose another username.");
        }

        if (sameEmail != null) {
            if (sameUsername != null && !sameUsername.getId().equals(sameEmail.getId())) {
                throw new IllegalArgumentException("Username already exists. Please choose another username.");
            }

            if (sameEmail.hasRole(requestedRole)) {
                throw new IllegalArgumentException("You have already registered as " + requestedRole + ".");
            }

            Role existingRole = sameEmail.getRoles().stream()
                    .filter(role -> role != requestedRole)
                    .findFirst()
                    .orElse(null);

            if (!confirmCrossRole) {
                String roleText = existingRole == null ? "another role" : existingRole.name();
                String message = "You have already registered as " + roleText
                        + ". Do you want to continue registering as " + requestedRole + "?";
                throw new CrossRoleRegistrationRequiredException(existingRole, requestedRole, message);
            }

            sameEmail.addRole(requestedRole);
            if (sameEmail.getFullName() == null || sameEmail.getFullName().isBlank()) {
                sameEmail.setFullName(normalizedFullName);
            }
            userRepository.update(sameEmail);
            ensureRoleProfile(sameEmail.getId(), requestedRole, workUnit);
            return sameEmail;
        }

        List<User> users = userRepository.findAll();
        String nextId = IdGenerator.nextId("U", users.stream().map(User::getId).collect(Collectors.toList()));
        User user = new User(nextId, normalizedUsername, password, normalizedFullName, normalizedEmail,
                List.of(requestedRole), List.of());
        userRepository.add(user);
        ensureRoleProfile(user.getId(), requestedRole, workUnit);
        return user;
    }

    private void ensureRoleProfile(String userId, Role role, String workUnit) {
        if (role == Role.TA) {
            TAProfile profile = taProfileRepository.findByUserId(userId)
                    .orElse(new TAProfile(userId, "", "", List.of(), "", "", ""));
            taProfileRepository.upsert(profile);
            return;
        }

        MOProfile moProfile = moProfileRepository.findByUserId(userId)
                .orElse(new MOProfile(userId, "", "", ""));
        moProfile.setWorkUnit(workUnit == null ? "" : workUnit.trim());
        moProfileRepository.upsert(moProfile);
    }
}
