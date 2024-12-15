package com.tekup.greeting.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tekup.greeting.dao.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long>{
   List<Person> findByName(String name);
}