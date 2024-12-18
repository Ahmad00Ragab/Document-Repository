// package com.example.publicationdocuments.mapper;

// import com.example.publicationdocuments.dto.DocumentDTO;
// import com.example.publicationdocuments.model.Document;

// public class DocumentMapper {

//     // Convert Document entity to DocumentDTO
//     public static DocumentDTO toDTO(Document document) {
//         return new DocumentDTO(
//             document.getId(),
//             document.getTitre(),
//             document.getContenu(),
//             document.getDatePublication(),
//             document.getTypeFichier(),
//             document.getResume(),
//             document.getMotCle(),
//             document.getAuteur() != null ? document.getAuteur().getId() : null,
//             document.getAuteur() != null ? document.getAuteur().getNom() : null, // Map auteurName
//             document.getCategorie() != null ? document.getCategorie().getId() : null,
//             document.getCategorie() != null ? document.getCategorie(). : null, // Map categoryName
//             document.getCheminFichier();
//         );
//     }

//     // Convert DocumentDTO to Document entity
//     public static Document toEntity(DocumentDTO dto) {
//         Document document = new Document();
//         document.setId(dto.getId());
//         document.setTitre(dto.getTitre());
//         document.setContenu(dto.getContenu());
//         document.setDatePublication(dto.getDatePublication());
//         document.setTypeFichier(dto.getTypeFichier());
//         document.setResume(dto.getResume());
//         document.setMotCle(dto.getMotCle());
//         document.setCheminFichier(dto.getCheminFichier());
//         // Auteur and Categorie can be set separately using their IDs in the service layer
//         return document;
//     }
// }
