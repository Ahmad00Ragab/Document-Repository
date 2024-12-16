package com.example.publicationdocuments.model;

import jakarta.persistence.*;


import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String contenu;
    
    @Column(name = "date_publication")
    private LocalDate datePublication;
    private String typeFichier;
    private String resume;
    private String motCle;

    @ManyToOne
    @JoinColumn(name = "auteur_id", referencedColumnName = "id")
    private Auteur auteur;

    @ManyToOne
    @JoinColumn(name = "categorie_id", referencedColumnName = "id")
    private Categorie categorie;

    private String cheminFichier;  // Ce champ contiendra le chemin du fichier téléchargé
}