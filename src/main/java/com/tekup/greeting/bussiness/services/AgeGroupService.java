package com.tekup.greeting.bussiness.services;

import java.util.List;

import com.tekup.greeting.dao.entities.AgeGroup;

public interface AgeGroupService {
    AgeGroup addAgeGroup(AgeGroup ageGroup);
    AgeGroup getAgeGroupById(Long id);
    List<AgeGroup> getAllAgeGroups();
    void deleteAgeGroup(Long id);
    AgeGroup updateAgeGroup(Long id, AgeGroup ageGroup);
}