package com.example.publicationdocuments.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.publicationdocuments.exceptions.ResourceNotFoundException;
import com.example.publicationdocuments.forms.DocumentForm;
import com.example.publicationdocuments.mapper.DocumentMapper;
import com.example.publicationdocuments.model.Document;
import com.example.publicationdocuments.repository.AuteurRepository;
import com.example.publicationdocuments.repository.CategorieRepository;
import com.example.publicationdocuments.repository.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;    
    
    @Autowired
    private AuteurRepository auteurRepository;    
    
    @Autowired
    private  CategorieRepository categorieRepository;

    // Méthode pour obtenir tous les documents (sans pagination)
    public List<DocumentForm> findAll() {
        return documentRepository.findAll()
                                 .stream()
                                 .map(document -> DocumentMapper.toDTO(document))
                                 .collect(Collectors.toList());
    }   
    
    
    public Page<DocumentForm> findAllWithPagination(Pageable pageable) {
         Page<Document> documentPage  = documentRepository.findAll(pageable);
         List<DocumentForm> documents = documentPage.getContent()
                                                    .stream()
                                                    .map(document -> DocumentMapper.toDTO(document))
                                                    .collect(Collectors.toList());                      
        return  new PageImpl<>(documents, pageable, documentPage.getTotalElements());                       
    }
    
   

    // Method to get a document by its ID and convert it to a DTO
    public DocumentForm findById(Long id) {
        return documentRepository.findById(id)
                                .map(DocumentMapper::toDTO)
                                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + id));

    }


    // Méthode pour enregistrer un document
    public void save(DocumentForm document) {
        Document documentEntity = DocumentMapper.toEntity(document, auteurRepository,categorieRepository);
        documentRepository.save(documentEntity);
    }

    // Méthode pour supprimer un document par son ID
    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }

    

    // Méthode pour rechercher des documents par plusieurs critères     
    public Page<DocumentForm> searchDocuments(String motCle, String titre, String auteur, String theme, Pageable pageable) {
        Page<Document> documentsPage;
        if (motCle != null && titre != null && auteur != null && theme != null) {
            documentsPage =  documentRepository.findByMotCleContainingAndTitreContainingAndAuteurNomContainingOrAuteurPrenomContainingAndCategorieThemeContaining(
                    motCle, titre, auteur, auteur, theme, pageable);
        } else if (motCle != null) {
            documentsPage =  documentRepository.findByMotCleContaining(motCle, pageable);
        } else if (titre != null) {
            documentsPage =  documentRepository.findByTitreContaining(titre, pageable);
        } else if (auteur != null) {
            documentsPage =  documentRepository.findByAuteurNomContainingOrAuteurPrenomContaining(auteur, auteur, pageable);
        } else if (theme != null) {
            documentsPage = documentRepository.findByCategorieThemeContaining(theme, pageable);
        } else {
            documentsPage =  documentRepository.findAll(pageable);
        }
        List<DocumentForm> documents = documentsPage.getContent()
                                                    .stream()
                                                    .map(document->DocumentMapper.toDTO(document))
                                                    .collect(Collectors.toList());

        return new PageImpl<>(documents, pageable,documentsPage.getTotalElements());
    }
    

}