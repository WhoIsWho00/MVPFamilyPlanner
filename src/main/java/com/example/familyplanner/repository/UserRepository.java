package com.example.familyplanner.repository;

import com.example.familyplanner.entity.Role;
import com.example.familyplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    User findByName(String username);

    User findByRole(Role role);
}
