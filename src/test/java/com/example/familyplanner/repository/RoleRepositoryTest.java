package com.example.familyplanner.repository;

import com.example.familyplanner.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByRoleName() {
        // Arrange
        Role role = new Role();
        role.setRoleName("ADMIN");
        roleRepository.save(role);

        // Act
        Optional<Role> foundRole = roleRepository.findByRoleName("ADMIN");

        // Assert
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getRoleName()).isEqualTo("ADMIN");
    }
}
