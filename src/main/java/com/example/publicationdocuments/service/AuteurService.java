package com.example.publicationdocuments.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.publicationdocuments.model.Auteur;
import com.example.publicationdocuments.repository.AuteurRepository;

@Service
public class AuteurService  implements UserDetailsService{

    private AuteurRepository auteurRepository;
    private PasswordEncoder passwordEncoder;    
    
    
    /* Security Part */
    @Autowired
	public AuteurService(AuteurRepository auteurRepository, PasswordEncoder passwordEncoder){
		this.auteurRepository  = auteurRepository;
		this.passwordEncoder = passwordEncoder;


		Optional<Auteur> auteur = auteurRepository.findByEmail("admin@admin.com");
        if (!auteur.isPresent()) {
            Auteur adminAuteur = new Auteur();
            adminAuteur.setNom(("admin"));
            adminAuteur.setPrenom("admin");
            adminAuteur.setEmail("admin@admin.com");
            adminAuteur.setPassword(passwordEncoder.encode("admin"));
            adminAuteur.setRoles(List.of("ROLE_ADMIN"));
            auteurRepository.save(adminAuteur);
            System.out.println("Admin Auteur created successfully");
        } else {
            System.out.println("Auteur found: " + auteur.get());
        }
	}
   
   
    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<Auteur> optAuteur = auteurRepository.findByEmail(email);

		org.springframework.security.core.userdetails.User springUser = null;

		if (optAuteur.isEmpty()) {
			throw new UsernameNotFoundException("Auteur with email: " + email + " not found");
		}
		
        Auteur auteur = optAuteur.get();
		List<String> roles = auteur.getRoles();
		Set<GrantedAuthority> ga = new HashSet<>();
		for (String role : roles) {
			ga.add(new SimpleGrantedAuthority(role));
		}

		springUser = new org.springframework.security.core.userdetails.User(
				email,
				auteur.getPassword(),
				ga);
		return springUser;
	}
   
   
   

    /* CRUD Operations Part */
    public List<Auteur> findAll() {
        return auteurRepository.findAll();
    }
    // Méthode findAll avec tri
    public List<Auteur> findAll(Sort sort) {
        return auteurRepository.findAll(sort);
    }

    public Auteur findById(Long id) {
        return auteurRepository.findById(id).orElse(null);
    }

    public void save(Auteur auteur) {
        auteurRepository.save(auteur);
    }

    public void deleteById(Long id) {
        auteurRepository.deleteById(id);
    }
}
