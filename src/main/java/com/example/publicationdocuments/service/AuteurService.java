package com.example.publicationdocuments.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.publicationdocuments.dto.AuteurForm;
import com.example.publicationdocuments.exceptions.ResourceNotFoundException;
import com.example.publicationdocuments.mapper.AuteurMapper;
import com.example.publicationdocuments.model.Auteur;
import com.example.publicationdocuments.repository.AuteurRepository;

@Service
public class AuteurService {

    @Autowired
    private AuteurRepository auteurRepository;


    public List<Auteur> findAll() {
        return auteurRepository.findAll();
    }

    
    // Méthode findAll avec tri et conversion vers DTO
    public List<AuteurForm> findAll(Sort sort) {
        List<Auteur> auteurs = auteurRepository.findAll(sort); // Fetch sorted entities
        return auteurs.stream()
                      .map(AuteurMapper::toDTO) // Map each entity to a DTO
                      .collect(Collectors.toList()); // Collect as a list of DTOs
    }

    public AuteurForm findById(Long id) {
        return auteurRepository.findById(id)
                               .map(AuteurMapper::toDTO) 
                               .orElseThrow(() -> new ResourceNotFoundException("Auteur not found with ID: " + id));
    }

    public AuteurForm save(AuteurForm dto) {
        Auteur auteur = AuteurMapper.toEntity(dto); // Map DTO to entity
        Auteur savedAuteur = auteurRepository.save(auteur);
        return AuteurMapper.toDTO(savedAuteur); // Map saved entity back to DTO
    }

    public void deleteById(Long id) {
        auteurRepository.deleteById(id);
    }

    public AuteurForm update(Long id, AuteurForm dto) {
        Auteur existingAuteur = auteurRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Auteur not found"));
        existingAuteur.setNom(dto.getNom());
        existingAuteur.setPrenom(dto.getPrenom());
        // Update other fields if necessary
        Auteur updatedAuteur = auteurRepository.save(existingAuteur);
        return AuteurMapper.toDTO(updatedAuteur);
    }
    
}