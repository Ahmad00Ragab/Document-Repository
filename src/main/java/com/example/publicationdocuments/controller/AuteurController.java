package com.example.publicationdocuments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.publicationdocuments.model.Auteur;
import com.example.publicationdocuments.service.AuteurService;

@Controller
@RequestMapping("/auteurs")
public class AuteurController {

    @Autowired
    private AuteurService auteurService;

    @GetMapping
    public String listAuteurs(Model model, String order) {
        // Création du tri basé sur la valeur de "order" (ascendant ou descendant)
        Sort sort = Sort.by(Sort.Order.asc("nom"));
        if ("desc".equals(order)) {
            sort = Sort.by(Sort.Order.desc("nom"));
        }

        // Récupération des auteurs triés
        List<Auteur> auteurs = auteurService.findAll(sort);

        model.addAttribute("auteurs", auteurs);
        model.addAttribute("order", order);  // Passer l'option de tri à la vue pour pré-cocher le bon radio button
        return "auteurs/list";
    }

    @GetMapping("/new")
    public String createAuteurForm(Model model) {
        model.addAttribute("auteur", new Auteur());
        return "auteurs/new";
    }

    @PostMapping
    public String saveAuteur(@ModelAttribute("auteur") Auteur auteur) {
        auteurService.save(auteur);
        return "redirect:/auteurs";
    }

    @GetMapping("/edit/{id}")
public String editAuteurForm(@PathVariable Long id, Model model) {
    Auteur auteur = auteurService.findById(id);
    if (auteur == null) {
        // Si l'auteur n'est pas trouvé, redirigez ou affichez un message d'erreur
        return "redirect:/auteurs"; // Redirige vers la liste des auteurs
    }
    model.addAttribute("auteur", auteur);
    return "auteurs/edit";  // Assurez-vous que le chemin correspond bien à l'emplacement de votre vue
}

@PostMapping("/update/{id}")
public String updateAuteur(@PathVariable Long id, @ModelAttribute("auteur") Auteur auteur) {
    // Assurez-vous que l'id est bien passé et que l'objet Auteur contient les informations mises à jour
    Auteur existingAuteur = auteurService.findById(id);
    if (existingAuteur != null) {
        existingAuteur.setNom(auteur.getNom());
        // Mettez à jour d'autres champs si nécessaire
        auteurService.save(existingAuteur);
    }
    return "redirect:/auteurs";  // Redirige vers la liste des auteurs après la mise à jour
}


    @GetMapping("/delete/{id}")
    public String deleteAuteur(@PathVariable Long id) {
        auteurService.deleteById(id);
        return "redirect:/auteurs";
    }
}

