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

import com.example.publicationdocuments.model.Categorie;
import com.example.publicationdocuments.service.CategorieService;

@Controller
@RequestMapping("/categorie")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @GetMapping
    public String listCategories(Model model, String order) {
        // Création du tri basé sur la valeur de "order" (ascendant ou descendant)
        Sort sort = Sort.by(Sort.Order.asc("theme"));
        if ("desc".equals(order)) {
            sort = Sort.by(Sort.Order.desc("theme"));
        }

        // Récupération des catégories triées
        List<Categorie> categories = categorieService.findAll(sort);

        model.addAttribute("categories", categories);
        model.addAttribute("order", order);  // Passer l'option de tri à la vue pour pré-cocher le bon radio button
        return "categorie/list";
    }

    @GetMapping("/new")
    public String createCategorieForm(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "categorie/new";
    }

    @PostMapping
    public String saveCategorie(@ModelAttribute("categorie") Categorie categorie) {
        categorieService.save(categorie);
        return "redirect:/categorie";
    }

    @GetMapping("/edit/{id}")
public String editCategorieForm(@PathVariable Long id, Model model) {
    Categorie categorie = categorieService.findById(id);
    if (categorie == null) {
        // Si la catégorie n'est pas trouvée, redirigez ou affichez un message d'erreur
        return "redirect:/categories"; // Redirige vers la liste des catégories
    }
    model.addAttribute("categorie", categorie);
    return "categorie/edit";  // Assurez-vous que le chemin correspond bien à l'emplacement de votre vue
}

@PostMapping("/update/{id}")
public String updateCategorie(@PathVariable Long id, @ModelAttribute("categorie") Categorie categorie) {
    // Assurez-vous que l'id est bien passé et que l'objet Categorie contient les informations mises à jour
    Categorie existingCategorie = categorieService.findById(id);
    if (existingCategorie != null) {
        existingCategorie.setTheme(categorie.getTheme());
        // Mettez à jour d'autres champs si nécessaire
        categorieService.save(existingCategorie);
    }
    return "redirect:/categorie";  // Redirige vers la liste des catégories après la mise à jour
}


    @GetMapping("/delete/{id}")
    public String deleteCategorie(@PathVariable Long id) {
        categorieService.deleteById(id);
        return "redirect:/categorie";
    }
}
