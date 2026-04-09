package com.example.tarecruitment.service;

import com.example.tarecruitment.model.Role;

public class CrossRoleRegistrationRequiredException extends RuntimeException {
    private final Role existingRole;
    private final Role requestedRole;

    public CrossRoleRegistrationRequiredException(Role existingRole, Role requestedRole, String message) {
        super(message);
        this.existingRole = existingRole;
        this.requestedRole = requestedRole;
    }

    public Role getExistingRole() {
        return existingRole;
    }

    public Role getRequestedRole() {
        return requestedRole;
    }
}
