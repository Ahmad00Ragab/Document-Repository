package com.example.publicationdocuments.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;



@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Auteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents;

    @Column(name="password")
    private String password;

    @Column(name="auteur_email", unique = true)
	private String email;

	@ElementCollection(fetch= FetchType.EAGER)
	@CollectionTable(
			name="roles",
			joinColumns = @JoinColumn(name="Auteur_id")
			)
	@Column(name="Auteur_role")
	private List<String> roles;
}
