package com.tekup.greeting.web.models.requests;

import com.tekup.greeting.dao.entities.AgeGroup;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonForm {
    @NotBlank(message = "Nom obligatoir!")
    private String name;
    @NotNull
    @Min(value=2, message="veuillez saisir age >=2")
    @Max(99)
    private short age;
    private String photo;
    private AgeGroup ageGroup;
}