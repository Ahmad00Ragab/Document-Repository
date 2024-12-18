package com.example.publicationdocuments.forms;

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
    private Long auteurId;               // Reference to the Auteur by ID
    private String auteurName;           // Reference to the Auteur by Name
    private String categorieName;        // Reference to the Categorie by ID
    private Long categorieId;            // Reference to the Categorie by Name
    private String cheminFichier;
}
