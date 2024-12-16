package com.example.publicationdocuments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Document> searchDocuments(String motCle, String titre, String auteur, String theme) {
        if (motCle != null && titre != null && auteur != null && theme != null) {
            return documentRepository.findByMotCleContainingAndTitreContainingAndAuteurNomContainingOrAuteurPrenomContainingAndCategorieThemeContaining(
                    motCle, titre, auteur, auteur, theme);
        } else if (motCle != null) {
            return documentRepository.findByMotCleContaining(motCle);
        } else if (titre != null) {
            return documentRepository.findByTitreContaining(titre);
        } else if (auteur != null) {
            return documentRepository.findByAuteurNomContainingOrAuteurPrenomContaining(auteur, auteur);
        } else if (theme != null) {
            return documentRepository.findByCategorieThemeContaining(theme);
        } else {
            return documentRepository.findAll();
        }
    }
    

}