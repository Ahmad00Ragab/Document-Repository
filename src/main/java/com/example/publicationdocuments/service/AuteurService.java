package com.example.publicationdocuments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.publicationdocuments.model.Auteur;
import com.example.publicationdocuments.repository.AuteurRepository;

@Service
public class AuteurService {

    @Autowired
    private AuteurRepository auteurRepository;


    public List<Auteur> findAll() {
        return auteurRepository.findAll();
    }

    
    // Méthode findAll avec tri
    public List<Auteur> findAll(Sort sort) {
        return auteurRepository.findAll(sort);
    }

    public Auteur findById(Long id) {
        return auteurRepository.findById(id).orElse(null);
    }

    public void save(Auteur auteur) {
        auteurRepository.save(auteur);
    }

    public void deleteById(Long id) {
        auteurRepository.deleteById(id);
    }
}