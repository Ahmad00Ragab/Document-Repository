package com.example.publicationdocuments.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.publicationdocuments.model.Auteur;
import com.example.publicationdocuments.model.Categorie;
import com.example.publicationdocuments.model.Document;
import com.example.publicationdocuments.service.AuteurService;
import com.example.publicationdocuments.service.CategorieService;
import com.example.publicationdocuments.service.DocumentService;
import com.example.publicationdocuments.forms.*;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AuteurService auteurService;

    @Autowired
    private CategorieService categorieService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    // Afficher la liste des documents avec pagination
    @GetMapping
    public String listDocuments(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 3; // Number of documents per page
        List<DocumentForm> totalDocuments = documentService.findAll();

        Page<DocumentForm> documents = documentService.findAllWithPagination(PageRequest.of(page, size));
        System.out.println("Total number of documents: " + totalDocuments.size());
        System.out.println("Documents on current page: " + documents.getContent());
        
        model.addAttribute("documents", documents);
        model.addAttribute("auteurs", auteurService.findAll());
        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("totalDocumentsCount", totalDocuments.size());
        model.addAttribute("totalPages", documents.getTotalPages());
        model.addAttribute("currentPage", page);
        return "documents/list";
    }

    
    // Afficher la liste des documents avec pagination
    @GetMapping("/user")
    public String listDocumentsForUser(@RequestParam(defaultValue = "0") int page, Model model) {
        int size = 10; // Number of documents per page
        List<DocumentForm> totalDocuments = documentService.findAll();
        Page<DocumentForm> documents = documentService.findAllWithPagination(PageRequest.of(page, size));
        System.out.println("Total number of documents: " + totalDocuments.size());
        System.out.println("Documents on current page: " + documents.getContent());

        model.addAttribute("documents", documents);
        model.addAttribute("auteurs", auteurService.findAll());
        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("totalDocumentsCount", totalDocuments.size());
        model.addAttribute("totalPages", documents.getTotalPages());
        model.addAttribute("currentPage", page);
        return "documents/list-user";
    }

    // Endpoint to download files
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        System.out.println("file name : " + filename);
        try {
            // Define the path to the upload directory
            Path filePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "uploads",
                    filename);

            // Check if the file exists
            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("Le fichier demandé est introuvable.");
            }

            // Load the file as a resource
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("Impossible de lire le fichier.");
            }

            // Prepare the response with headers
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (FileNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search")
    public String searchDocuments(@RequestParam(required = false) String motCle,
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String auteur,
            @RequestParam(required = false) String theme,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        int size = 3; // Number of documents per page
        Page<DocumentForm> documents = documentService.searchDocuments(motCle, titre, auteur, theme,
                PageRequest.of(page, size));

        model.addAttribute("documents", documents);
        // model.addAttribute("totalDocumentsCount", documentsPage.getTotalElements());
        model.addAttribute("totalPages", documents.getTotalPages());
        model.addAttribute("currentPage", page);

        if (documents.isEmpty()) {
            model.addAttribute("message", "Aucun document trouvé pour les critères de recherche donnés.");
        }
        model.addAttribute("auteurs", auteurService.findAll());
        model.addAttribute("categories", categorieService.findAll());
        return "documents/list";
    }

    // Formulaire pour créer un nouveau document
    @GetMapping("/new")
    public String createDocumentForm(Model model) {
        model.addAttribute("document", new DocumentForm());
        model.addAttribute("auteurs", auteurService.findAll());
        model.addAttribute("categories", categorieService.findAll());
        return "documents/new";
    }

    @PostMapping("/create")
    public String saveDocument(@ModelAttribute DocumentForm document,
            @RequestParam("auteur.id") Long auteurId,
            @RequestParam("categorie.id") Long categorieId,
            @RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String documentFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path documentPath = Paths.get(UPLOAD_DIR + documentFileName);

                Files.createDirectories(documentPath.getParent());
                Files.write(documentPath, file.getBytes());

                document.setCheminFichier("/uploads/" + documentFileName);
            }
            document.setAuteurId(auteurId);
            document.setCategorieId(categorieId);
            documentService.save(document);
            return "redirect:/documents";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/documents";
        }
    }

    // Supprimer un document
    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id) {
        documentService.deleteById(id);
        return "redirect:/documents";
    }

    // Formulaire pour éditer un document existant
    @GetMapping("/edit/{id}")
    public String editDocumentForm(@PathVariable Long id, Model model) {
        DocumentForm document = documentService.findById(id);
        if (document == null) {
            return "redirect:/documents?error=documentNotFound";
        }
        model.addAttribute("document", document);
        model.addAttribute("auteurs", auteurService.findAll());
        model.addAttribute("categories", categorieService.findAll());

        return "documents/edit";
    }

    // Mise à jour d'un document
    @PostMapping("/update/{id}")
    public String updateDocument(@PathVariable Long id,
            @ModelAttribute DocumentForm document,
            @RequestParam("auteur.id") Long auteurId,
            @RequestParam("categorie.id") Long categorieId,
            @RequestParam("file") MultipartFile file) throws IOException {
        DocumentForm existingDocument = documentService.findById(id);
        if (existingDocument == null) {
            return "redirect:/documents?error=documentNotFound";
        }

        existingDocument.setTitre(document.getTitre());
        existingDocument.setContenu(document.getContenu());
        existingDocument.setTypeFichier(document.getTypeFichier());
        existingDocument.setResume(document.getResume());
        existingDocument.setMotCle(document.getMotCle());
        existingDocument.setDatePublication(document.getDatePublication());

        AuteurForm auteur = auteurService.findById(auteurId);
        Categorie categorie = categorieService.findById(categorieId);

        if (auteur != null && categorie != null) {
            existingDocument.setAuteurId(auteurId);
            existingDocument.setCategorieId(categorieId);

            if (!file.isEmpty()) {
                String documentFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path documentPath = Paths.get(UPLOAD_DIR + documentFileName);

                Files.createDirectories(documentPath.getParent());
                Files.write(documentPath, file.getBytes());

                existingDocument.setCheminFichier("/uploads/" + documentFileName);
            }

            documentService.save(existingDocument);
            return "redirect:/documents";
        } else {
            return "redirect:/documents/edit/" + id + "?error=invalid";
        }
    }
}
