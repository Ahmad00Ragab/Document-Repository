package com.example.publicationdocuments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.publicationdocuments.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findByMotCleContainingAndTitreContainingAndAuteurNomContainingOrAuteurPrenomContainingAndCategorieThemeContaining(
            String motCle, String titre, String auteurNom, String auteurPrenom, String theme, Pageable pageable);

    Page<Document> findByMotCleContaining(String motCle, Pageable pageable);

    Page<Document> findByTitreContaining(String titre, Pageable pageable);

    Page<Document> findByAuteurNomContainingOrAuteurPrenomContaining(String auteurNom, String auteurPrenom, Pageable pageable);

    Page<Document> findByCategorieThemeContaining(String theme, Pageable pageable);

    Page<Document> findAll(Pageable pageable);
}

