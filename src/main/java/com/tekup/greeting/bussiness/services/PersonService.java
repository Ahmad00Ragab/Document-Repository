package com.tekup.greeting.bussiness.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tekup.greeting.dao.entities.Person;

public interface PersonService {
    //Read opérations
    List<Person> getAllPerson();
    Person getPersonById(Long id) /*throws Exception*/;
    List<Person> getPersonsByName(String name); 
    List<Person> getPersonSortedByAge(String order);
    Page<Person> getAllPersonPagination(Pageable pegeable);
    Page<Person> getPersonSortedByAgePagination(String order,Pageable pegeable);
    //create
    Person addPerson(Person person);
    //Update
    Person updatePerson(Person person);
    //Delete
    void deletePersonById(Long id);


}