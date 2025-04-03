package com.example.familyplanner.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FamilyTest {

    @Test
    void testFamilyEntity() {
        UUID id = UUID.randomUUID();
        UUID adminUserId = UUID.randomUUID();
        UUID inviteCode = UUID.randomUUID();
        String name = "Test Family";

        Family family = new Family(id, name, adminUserId, inviteCode);

        assertEquals(id, family.getId());
        assertEquals(name, family.getName());
        assertEquals(adminUserId, family.getAdminUserId());
        assertEquals(inviteCode, family.getInviteCode());
    }
}
