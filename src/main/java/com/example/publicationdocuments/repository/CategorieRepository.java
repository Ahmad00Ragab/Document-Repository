package com.example.publicationdocuments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.publicationdocuments.model.Categorie;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    // Méthode pour récupérer toutes les catégories triées par thème (ordre croissant)
    List<Categorie> findAllByOrderByThemeAsc();

    // Méthode pour récupérer toutes les catégories triées par thème (ordre décroissant)
    List<Categorie> findAllByOrderByThemeDesc();
}
