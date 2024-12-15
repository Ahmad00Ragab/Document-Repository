package com.tekup.greeting.dao.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="age_groups")
public class AgeGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="group_name", nullable = false,unique = true)
    private String groupName;
    @Column(name="min_age",nullable = false)
    private int minAge;
    @Column(name="max_age",nullable = false)
    private int maxAge;
    @OneToMany(mappedBy = "ageGroup",cascade = CascadeType.ALL)
    private List<Person> persons;
    public AgeGroup(Long id, String groupName, int minAge, int maxAge) {
        this.id = id;
        this.groupName = groupName;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }    
}