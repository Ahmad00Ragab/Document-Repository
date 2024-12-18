package com.example.publicationdocuments.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.publicationdocuments.model.Document;
import com.example.publicationdocuments.repository.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    // Méthode pour obtenir tous les documents (sans pagination)
    public List<Document> findAll() {
        return documentRepository.findAll();
    }   
    
    public Page<Document> findAll(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    public Page<Document> findAllWithPagination(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

   

    // Méthode pour obtenir un document par son ID
    public Document findById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    // Méthode pour enregistrer un document
    public void save(Document document) {
        documentRepository.save(document);
    }

    // Méthode pour supprimer un document par son ID
    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }

    

    // Méthode pour rechercher des documents par plusieurs critères     
    public Page<Document> searchDocuments(String motCle, String titre, String auteur, String theme, Pageable pageable) {
        if (motCle != null && titre != null && auteur != null && theme != null) {
            return documentRepository.findByMotCleContainingAndTitreContainingAndAuteurNomContainingOrAuteurPrenomContainingAndCategorieThemeContaining(
                    motCle, titre, auteur, auteur, theme, pageable);
        } else if (motCle != null) {
            return documentRepository.findByMotCleContaining(motCle, pageable);
        } else if (titre != null) {
            return documentRepository.findByTitreContaining(titre, pageable);
        } else if (auteur != null) {
            return documentRepository.findByAuteurNomContainingOrAuteurPrenomContaining(auteur, auteur, pageable);
        } else if (theme != null) {
            return documentRepository.findByCategorieThemeContaining(theme, pageable);
        } else {
            return documentRepository.findAll(pageable);
        }
    }
    

}