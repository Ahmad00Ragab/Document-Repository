package com.example.publicationdocuments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.publicationdocuments.exceptions.ResourceNotFoundException;
import com.example.publicationdocuments.model.Categorie;
import com.example.publicationdocuments.repository.CategorieRepository;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    // Méthode pour récupérer toutes les catégories
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    // Méthode findAll avec tri
    public List<Categorie> findAll(Sort sort) {
        return categorieRepository.findAll(sort);
    }

    // Méthode pour récupérer une catégorie par son ID
    public Categorie findById(Long id) {
        return categorieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categorie not found with ID: " + id));
    }

    // Méthode pour sauvegarder une catégorie
    public void save(Categorie categorie) {
        categorieRepository.save(categorie);
    }

    // Méthode pour supprimer une catégorie par son ID
    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }
}
