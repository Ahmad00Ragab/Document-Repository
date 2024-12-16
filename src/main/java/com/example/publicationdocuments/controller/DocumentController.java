package com.example.publicationdocuments.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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
import java.util.List;
import com.example.publicationdocuments.model.Auteur;
import com.example.publicationdocuments.model.Categorie;
import com.example.publicationdocuments.model.Document;
import com.example.publicationdocuments.service.AuteurService;
import com.example.publicationdocuments.service.CategorieService;
import com.example.publicationdocuments.service.DocumentService;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private AuteurService auteurService;

    @Autowired
    private CategorieService categorieService;

    // Afficher la liste des documents
    @GetMapping
    public String listDocuments(Model model, @RequestParam(required = false) String order) {
        model.addAttribute("documents", documentService.findAll());
        return "documents/list";
    } 
    // Formulaire pour rechercher des documents
    @GetMapping("/search")
    public String searchDocuments(@RequestParam(required = false) String motCle,
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String auteur,
            @RequestParam(required = false) String theme,
            Model model) {
        // Appeler le service pour effectuer la recherche avec les paramètres fournis
        List<Document> documents = documentService.searchDocuments(motCle, titre, auteur, theme);

        // Ajouter les résultats de la recherche à l'attribut "documents"
        model.addAttribute("documents", documents);

        // Ajouter un message si aucun document n'est trouvé
        if (documents.isEmpty()) {
            model.addAttribute("message", "Aucun document trouvé pour les critères de recherche donnés.");
        }

        // Ajouter les autres éléments nécessaires pour le formulaire de recherche (optionnel)
        model.addAttribute("auteurs", auteurService.findAll());
        model.addAttribute("categories", categorieService.findAll());

        return "documents/list";
    }

    // Formulaire pour créer un nouveau document
    @GetMapping("/new")
    public String createDocumentForm(Model model) {
        model.addAttribute("document", new Document());
        model.addAttribute("auteurs", auteurService.findAll());
        model.addAttribute("categories", categorieService.findAll());
        return "documents/new";
    }

    // Enregistrer un nouveau document avec l'upload de fichier
    @PostMapping
    public String saveDocument(@ModelAttribute Document document,
                               @RequestParam("auteur.id") Long auteurId,
                               @RequestParam("categorie.id") Long categorieId,
                               @RequestParam("file") MultipartFile file) throws IOException {
        Auteur auteur = auteurService.findById(auteurId);
        Categorie categorie = categorieService.findById(categorieId);

        if (auteur != null && categorie != null) {
            document.setAuteur(auteur);
            document.setCategorie(categorie);

            // Si un fichier est téléchargé, l'enregistrer sur le serveur
            if (!file.isEmpty()) {
                String filePath = saveUploadedFile(file);
                document.setCheminFichier(filePath);
            }

            documentService.save(document);
            return "redirect:/documents"; // Rediriger vers la liste des documents après l'enregistrement
        } else {
            return "redirect:/documents/new?error=invalid"; // Redirection avec message d'erreur
        }
    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        String uploadDir = "C:\\Users\\DELL\\Desktop";

        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = path.resolve(fileName);

        file.transferTo(filePath.toFile());

        return "/uploads/" + fileName;
    }

    // Supprimer un document
    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id) {
        documentService.deleteById(id);
        return "redirect:/documents"; // Rediriger vers la liste des documents après la suppression
    }

    // Formulaire pour éditer un document existant
    @GetMapping("/edit/{id}")
    public String editDocumentForm(@PathVariable Long id, Model model) {
        Document document = documentService.findById(id);
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
                                 @ModelAttribute Document document,
                                 @RequestParam("auteur.id") Long auteurId,
                                 @RequestParam("categorie.id") Long categorieId,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        Document existingDocument = documentService.findById(id);
        if (existingDocument == null) {
            return "redirect:/documents?error=documentNotFound";
        }

        // Mettre à jour les informations du document
        existingDocument.setTitre(document.getTitre());
        existingDocument.setContenu(document.getContenu());
        existingDocument.setTypeFichier(document.getTypeFichier());
        existingDocument.setResume(document.getResume());
        existingDocument.setMotCle(document.getMotCle());
        existingDocument.setDatePublication(document.getDatePublication());

        Auteur auteur = auteurService.findById(auteurId);
        Categorie categorie = categorieService.findById(categorieId);

        if (auteur != null && categorie != null) {
            existingDocument.setAuteur(auteur);
            existingDocument.setCategorie(categorie);

            // Si un fichier est téléchargé, mettre à jour le fichier
            if (!file.isEmpty()) {
                String filePath = saveUploadedFile(file);
                existingDocument.setCheminFichier(filePath);
            }

            documentService.save(existingDocument);
            return "redirect:/documents";
        } else {
            return "redirect:/documents/edit/" + id + "?error=invalid";
        }
    }

    // Nouvelle méthode pour télécharger un fichier
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get("C:\\Users\\DELL\\Desktop").resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            throw new IOException("File not found");
        }
    }
}