package com.example.familyplanner.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.familyplanner.entity.Role;
import com.example.familyplanner.entity.User;

//нужны тесты
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

//     Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);
}
