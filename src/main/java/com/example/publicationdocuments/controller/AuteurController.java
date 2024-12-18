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

import com.example.publicationdocuments.dto.AuteurForm;
import com.example.publicationdocuments.service.AuteurService;

@Controller
@RequestMapping("/auteurs")
public class AuteurController {

    @Autowired
    private AuteurService auteurService;

    @GetMapping
    public String listAuteurs(Model model, String order) {
        // Define sorting order based on the "order" parameter
        Sort sort = Sort.by(Sort.Order.asc("nom"));
        if ("desc".equals(order)) {
            sort = Sort.by(Sort.Order.desc("nom"));
        }

        // Retrieve sorted list of AuteurForm
        List<AuteurForm> auteurs = auteurService.findAll(sort);

        model.addAttribute("auteurs", auteurs);
        model.addAttribute("order", order); // Pass sorting option to the view
        return "auteurs/list";
    }

    @GetMapping("/new")
    public String createAuteurForm(Model model) {
        // Pass an empty AuteurForm for form binding
        model.addAttribute("auteur", new AuteurForm());
        return "auteurs/new";
    }

    @PostMapping
    public String saveAuteur(@ModelAttribute("auteur") AuteurForm auteur) {
        // Save the DTO using the service
        auteurService.save(auteur);
        return "redirect:/auteurs";
    }

    @GetMapping("/edit/{id}")
    public String editAuteurForm(@PathVariable Long id, Model model) {
        // Retrieve the AuteurForm by ID
        AuteurForm auteur = auteurService.findById(id);
        if (auteur == null) {
            // If not found, redirect to the list page
            return "redirect:/auteurs";
        }
        model.addAttribute("auteur", auteur);
        return "auteurs/edit"; // Ensure the view path matches your setup
    }

    @PostMapping("/update/{id}")
    public String updateAuteur(@PathVariable Long id, @ModelAttribute("auteur") AuteurForm auteur) {
        // Update the DTO using the service
        auteurService.update(id, auteur);
        return "redirect:/auteurs"; // Redirect to the list page after update
    }

    @GetMapping("/delete/{id}")
    public String deleteAuteur(@PathVariable Long id) {
        // Delete the author by ID
        auteurService.deleteById(id);
        return "redirect:/auteurs";
    }
}
