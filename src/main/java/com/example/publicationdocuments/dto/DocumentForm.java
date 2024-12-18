package com.example.publicationdocuments.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentForm {
    private Long id;
    private String titre;
    private String contenu;
    private LocalDate datePublication;
    private String typeFichier;
    private String resume;
    private String motCle;
    private Long auteurId;        // Reference to the Auteur by ID
    private Long categorieId;     // Reference to the Categorie by ID
    private String cheminFichier;
}
