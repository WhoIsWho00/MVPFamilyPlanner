package com.example.familyplanner.repository;

import com.example.familyplanner.entity.Task;
import com.example.familyplanner.entity.TaskStatus;
import com.example.familyplanner.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testFindByAssignedTo() {
        // ...создание и сохранение тестовых задач...
        Page<Task> tasks = taskRepository.findByAssignedTo(new User(), PageRequest.of(0, 10));
        assertThat(tasks).isNotEmpty();
    }

    @Test
    void testFindByFamilyId() {
        // ...создание и сохранение тестовых задач...
        Page<Task> tasks = taskRepository.findByFamilyId(UUID.randomUUID(), PageRequest.of(0, 10));
        assertThat(tasks).isNotEmpty();
    }

    @Test
    void testFindByCreatedBy() {
        // ...создание и сохранение тестовых задач...
        Page<Task> tasks = taskRepository.findByCreatedBy(new User(), PageRequest.of(0, 10));
        assertThat(tasks).isNotEmpty();
    }

    @Test
    void testFindByDueDateBetween() {
        // ...создание и сохранение тестовых задач...
        List<Task> tasks = taskRepository.findByDueDateBetween(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertThat(tasks).isNotEmpty();
    }

    @Test
    void testFindTasksWithFilters() {
        // ...создание и сохранение тестовых задач...
        Page<Task> tasks = taskRepository.findTasksWithFilters(
                UUID.randomUUID(), true, TaskStatus.IN_PROGRESS, UUID.randomUUID(), 1, PageRequest.of(0, 10));
        assertThat(tasks).isNotEmpty();
    }
}
