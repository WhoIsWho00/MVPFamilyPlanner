package com.example.familyplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.familyplanner.entity.SomeEntity;

import java.util.Date;

@Repository
public interface SomeEntityRepository extends JpaRepository<SomeEntity, Long> {
    int deleteByCreatedAtBefore(Date cutoffDate);
}
