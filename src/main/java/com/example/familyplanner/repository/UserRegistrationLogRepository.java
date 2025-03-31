package com.example.familyplanner.repository;

import com.example.familyplanner.entity.UserRegistrationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRegistrationLogRepository extends JpaRepository<UserRegistrationLog, Long> {
    @Query("SELECT COUNT(u) FROM UserRegistrationLog u WHERE u.ipAddress = :ip AND u.registrationTime >= :timeLimit")
    long countRecentRegistrations(@Param("ip") String ip, @Param("timeLimit") LocalDateTime timeLimit);
}
