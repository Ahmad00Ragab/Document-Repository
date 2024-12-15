package com.tekup.greeting.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekup.greeting.dao.entities.AgeGroup;

public interface AgeGroupRepository extends JpaRepository<AgeGroup, Long> {
    
}