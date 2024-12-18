package com.example.publicationdocuments.mapper;

import com.example.publicationdocuments.dto.DocumentForm;
import com.example.publicationdocuments.model.Auteur;
import com.example.publicationdocuments.model.Categorie;
import com.example.publicationdocuments.model.Document;
import com.example.publicationdocuments.repository.AuteurRepository;
import com.example.publicationdocuments.repository.CategorieRepository;

public class DocumentMapper {




    // Convert Document entity to DocumentDTO
    public static DocumentForm toDTO(Document document) {
        return new DocumentForm(
                document.getId(),
                document.getTitre(),
                document.getContenu(),
                document.getDatePublication(),
                document.getTypeFichier(),
                document.getResume(),
                document.getMotCle(),
                document.getAuteur() != null ? document.getAuteur().getId() : null,
                document.getCategorie() != null ? document.getCategorie().getId() : null,
                document.getCheminFichier());
    }






    // Convert DocumentForm to Document entity
    public static Document toEntity(DocumentForm documentForm, AuteurRepository auteurRepository, CategorieRepository categorieRepository) {
        Document document = new Document();
        document.setId(documentForm.getId());
        document.setTitre(documentForm.getTitre());
        document.setContenu(documentForm.getContenu());
        document.setDatePublication(documentForm.getDatePublication());
        document.setTypeFichier(documentForm.getTypeFichier());
        document.setResume(documentForm.getResume());
        document.setMotCle(documentForm.getMotCle());
        document.setCheminFichier(documentForm.getCheminFichier());

        // Fetch and set Auteur if ID is provided
        if (documentForm.getAuteurId() != null) {
            Auteur auteur = auteurRepository.findById(documentForm.getAuteurId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Invalid Auteur ID: " + documentForm.getAuteurId()));
            document.setAuteur(auteur);
        }

        // Fetch and set Categorie if ID is provided
        if (documentForm.getCategorieId() != null) {
            Categorie categorie = categorieRepository.findById(documentForm.getCategorieId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Invalid Categorie ID: " + documentForm.getCategorieId()));
            document.setCategorie(categorie);
        }

        return document;
    }

}
