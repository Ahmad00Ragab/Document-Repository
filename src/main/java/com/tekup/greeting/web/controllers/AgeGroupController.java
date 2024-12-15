package com.tekup.greeting.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.tekup.greeting.dao.entities.AgeGroup;
import com.tekup.greeting.web.models.requests.AgeGroupForm;

import jakarta.validation.Valid;

import java.util.List;
/*
 * CRUD operations MAP
 * Create : /ageGroups/create (Get: pour récupérer le fomulaire d'ajout d'un nouveau groupe d'age)
 *                          (Post: pour ajouter un groupe d'age à la table  age_groups)
 * Read : /ageGroups (Get)
 *      
 * 
 * Update : /ageGroups/{id}/edit (Get: pour récuperer le formulaire de modification d'un nouveau groupe d'age ) 
 *                             (Post : pour modifier un group d'age par son id)    
 * Delelte: /ageGroups/{id}/delete (Post : permet de supprimer un groupe d'age par son id)                     
 */
@Controller
@RequestMapping("/ageGroups")
public class AgeGroupController {

    @Autowired
    private com.tekup.greeting.bussiness.services.AgeGroupService ageGroupService;

    @GetMapping("")
    public String listAgeGroups(Model model) {
        List<AgeGroup> ageGroups = ageGroupService.getAllAgeGroups();
        model.addAttribute("ageGroups", ageGroups);
        return "ageGroup/ageGroupList"; // Nom du fichier HTML/Thymeleaf pour afficher la liste des groupes d'âge
    }

    @GetMapping("/create")
    public String showFormForAdd(Model model) {
        AgeGroupForm ageGroupForm = new AgeGroupForm();
        model.addAttribute("ageGroupForm", ageGroupForm);
        return "ageGroup/ageGroupAddForm"; // Nom du fichier HTML/Thymeleaf pour le formulaire d'ajout de groupe d'âge
    }

    @PostMapping("/create")
    public String saveAgeGroup(@Valid @ModelAttribute("ageGroupForm") AgeGroupForm ageGroupForm,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "ageGroup/ageGroupAddForm";
        }
        ageGroupService.addAgeGroup(new AgeGroup(null,ageGroupForm.getGroupName(),ageGroupForm.getMinAge(),ageGroupForm.getMaxAge()));
        return "redirect:/ageGroups"; // Rediriger vers la liste des groupes d'âge après l'ajout
    }

    @GetMapping("/{id}/edit")
    public String updateAgeGroup(@PathVariable Long id, Model model) {
        AgeGroup ageGroup = ageGroupService.getAgeGroupById(id);
        model.addAttribute("ageGroupForm", new AgeGroupForm(ageGroup.getGroupName(),ageGroup.getMinAge(),ageGroup.getMaxAge()));
        model.addAttribute("id", ageGroup.getId());
        return "ageGroup/ageGroupEditForm"; // Nom du fichier HTML/Thymeleaf pour le formulaire de mise à jour de groupe
                               // d'âge
    }

    @PostMapping("/{id}/edit")
    public String updateAgeGroup(@PathVariable Long id,@ModelAttribute("ageGroupForm") AgeGroupForm ageGroupForm) {
        ageGroupService.updateAgeGroup(id, new AgeGroup(null,ageGroupForm.getGroupName(),ageGroupForm.getMinAge(),ageGroupForm.getMaxAge()));
        return "redirect:/ageGroups"; // Rediriger vers la liste des groupes d'âge après la mise à jour
    }

    @PostMapping("/{id}/delete")
    public String deleteAgeGroup(@PathVariable Long id) {
        ageGroupService.deleteAgeGroup(id);
        return "redirect:/ageGroups"; // Rediriger vers la liste des groupes d'âge après la suppression
    }
}