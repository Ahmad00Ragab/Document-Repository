package com.example.publicationdocuments.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.publicationdocuments.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}