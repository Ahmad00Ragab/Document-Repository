package com.tekup.greeting.bussiness.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tekup.greeting.bussiness.services.AgeGroupService;
import com.tekup.greeting.dao.entities.AgeGroup;
import com.tekup.greeting.dao.repositories.AgeGroupRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AgeGroupServiceImpl  implements AgeGroupService{
   
    @Autowired
    private AgeGroupRepository ageGroupRepository;

    @Override
    public AgeGroup addAgeGroup(AgeGroup ageGroup) {
        if(ageGroup==null){
            return null;
        }
        return ageGroupRepository.save(ageGroup);
    }

    @Override
    public AgeGroup getAgeGroupById(Long id) {
        if(id==null){
            return null;
        }
        return this.ageGroupRepository
        .findById(id)
        .orElseThrow(()->new EntityNotFoundException("Age Group with id: "+id+" not found"));
    }

    @Override
    public List<AgeGroup> getAllAgeGroups() {
        return ageGroupRepository.findAll();
    }

    @Override
    public void deleteAgeGroup(Long id) {
        if(id==null){
            return ;
        } else if(this.ageGroupRepository.existsById(id)){
            this.ageGroupRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Age Group with id: "+id+" not found");
        }   
    }

    @Override
    public AgeGroup updateAgeGroup(Long id, AgeGroup ageGroup) {
        AgeGroup existingAgeGroup = this.getAgeGroupById(id);
            existingAgeGroup.setGroupName(ageGroup.getGroupName());
            existingAgeGroup.setMinAge(ageGroup.getMinAge());
            existingAgeGroup.setMaxAge(ageGroup.getMaxAge());
            return ageGroupRepository.save(existingAgeGroup);
       
    }
}