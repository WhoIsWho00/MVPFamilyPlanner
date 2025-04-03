package com.example.familyplanner.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testRoleEntity() {
        UUID id = UUID.randomUUID();
        String roleName = "ADMIN";

        Role role = new Role(id, roleName);

        assertEquals(id, role.getId());
        assertEquals(roleName, role.getRoleName());
    }

    @Test
    void testRoleConstructor() {
        String roleName = "USER";
        Role role = new Role(roleName);

        assertNull(role.getId());
        assertEquals(roleName, role.getRoleName());
    }
}
