package com.example.familyplanner.repository;

import com.example.familyplanner.entity.UserRegistrationLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRegistrationLogRepositoryTest {

    @Autowired
    private UserRegistrationLogRepository userRegistrationLogRepository;

    @Test
    void testCountRecentRegistrations() {
        // ...создание и сохранение тестовых записей регистрации...
        long count = userRegistrationLogRepository.countRecentRegistrations("127.0.0.1", LocalDateTime.now().minusHours(1));
        assertThat(count).isGreaterThan(0);
    }
}
