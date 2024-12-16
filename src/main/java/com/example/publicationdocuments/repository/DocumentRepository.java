package com.example.publicationdocuments.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.publicationdocuments.model.Document;
public interface DocumentRepository extends JpaRepository<Document, Long> {

   // Recherche par mot-clé
List<Document> findByMotCleContaining(String motCle);

   // Recherche par titre
List<Document> findByTitreContaining(String titre);

   // Recherche par auteur (en supposant que 'auteur' est une entité liée et qu'elle contient 'nom' ou 'prenom')
List<Document> findByAuteurNomContainingOrAuteurPrenomContaining(String nom, String prenom);

   // Recherche par catégorie (en supposant que 'categorie' est une entité liée avec un champ 'theme')
List<Document> findByCategorieThemeContaining(String theme);

   // Recherche combinée avec plusieurs critères
List<Document> findByMotCleContainingAndTitreContainingAndAuteurNomContainingOrAuteurPrenomContainingAndCategorieThemeContaining(
        String motCle, String titre, String nomAuteur, String prenomAuteur, String theme);

}

