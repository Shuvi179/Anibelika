package com.orion.anibelika.security;

import com.orion.anibelika.entity.Role;

public enum ApplicationSecurityRole {
    ROLE_ADMIN;

    public static Role getRole(ApplicationSecurityRole securityRole) {
        return new Role(securityRole.name());
    }
}
