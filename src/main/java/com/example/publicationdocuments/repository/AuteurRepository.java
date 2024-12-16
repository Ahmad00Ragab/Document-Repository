package com.example.publicationdocuments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.publicationdocuments.model.Auteur;
import java.util.Optional;


public interface AuteurRepository extends JpaRepository<Auteur, Long> {
    Optional<Auteur> findByEmail(String email);
}


