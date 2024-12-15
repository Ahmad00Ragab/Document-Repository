package com.tekup.greeting.bussiness.servicesImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tekup.greeting.bussiness.services.PersonService;
import com.tekup.greeting.dao.entities.Person;
import com.tekup.greeting.dao.repositories.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

   /* @Autowired
     PersonRepository personRepository;*/
     
     final PersonRepository personRepository;
     public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository=personRepository;
     }
      
    @Override
    public List<Person> getAllPerson() {
       return this.personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) /*throws Exception*/ {
        if(id==null){
            return null; 
        }
       return this.personRepository.findById(id).get();
       //return this.personRepository.findById(id).orElseThrow(()->new Exception());
    }

    @Override
    public Person addPerson(Person person) {
        if(person==null){
            return null;
        }
       return this.personRepository.save(person);
    }

    @Override
    public Person updatePerson(Person person) {
        if(person==null){
            return null;
        }
       return this.personRepository.save(person);  
    }

    @Override
    public void deletePersonById(Long id) {
        if(id==null){
            return ;
        }
         this.personRepository.deleteById(id);
    }

    @Override
    public List<Person> getPersonsByName(String name){
        return this.personRepository.findByName(name);
    }

    @Override
    public List<Person> getPersonSortedByAge(String order/* tow values :  "asc" / "desc" */) {
        //
        Sort.Direction direction= Sort.Direction.ASC;
        if("desc".equalsIgnoreCase(order)){
            direction= Sort.Direction.DESC;
        }
       // return personRepository.findAll(Sort.by(Direction.ASC,"age"));
       return personRepository.findAll(Sort.by(direction,"age"));
    }

    @Override
    public Page<Person> getAllPersonPagination(Pageable pegeable) {
        if(pegeable ==null){
            return null;
        }
        return this.personRepository.findAll(pegeable);

        /* page=0
         * person1
         * 
         * 
         * 
         * 
         * person5
         * page1---
         * person6
         * 
         * 
         * 
         * 
         * 
         * 
         * person10
         * page 2--
         * person11
         * 
         * 
         * 
         * 
         * 
         * person15
         * page 3--
         * person16
         * 
         * 
         * person18
         * 
         */
      
    }
    @Override
    public Page<Person> getPersonSortedByAgePagination(String order, Pageable pegeable) {
        if(pegeable ==null){
            return null;
        }  
        Sort.Direction direction= Sort.Direction.ASC;
        if("desc".equalsIgnoreCase(order)){
            direction= Sort.Direction.DESC;
        }
        Pageable sortedPageable=PageRequest.of(
            pegeable.getPageNumber(),
            pegeable.getPageSize(),
            Sort.by(direction,"age")
        );
        return this.personRepository.findAll(sortedPageable);
    }
}