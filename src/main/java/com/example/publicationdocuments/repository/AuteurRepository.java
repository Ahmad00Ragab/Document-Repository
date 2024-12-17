package com.example.publicationdocuments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.publicationdocuments.model.Auteur;

public interface AuteurRepository extends JpaRepository<Auteur, Long> {
}
