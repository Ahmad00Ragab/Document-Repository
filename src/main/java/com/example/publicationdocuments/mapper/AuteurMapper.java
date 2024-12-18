package com.example.publicationdocuments.mapper;


import com.example.publicationdocuments.dto.AuteurForm;
import com.example.publicationdocuments.model.Auteur;

public class AuteurMapper {
    
     // Convert Auteur entity to AuteurForm
    public static AuteurForm toDTO(Auteur auteur) {
        AuteurForm dto = new AuteurForm();
        dto.setId(auteur.getId());
        dto.setNom(auteur.getNom());
        dto.setPrenom(auteur.getPrenom());
        return dto;
    }

    // Convert AuteurForm to Auteur entity
    public static Auteur toEntity(AuteurForm dto) {
        Auteur auteur = new Auteur();
        auteur.setId(dto.getId());
        auteur.setNom(dto.getNom());
        auteur.setPrenom(dto.getPrenom());
        return auteur;
    }
}
