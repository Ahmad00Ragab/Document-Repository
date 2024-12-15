package com.tekup.greeting.web.models.requests;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgeGroupForm {
    @NotBlank
    @Size(min=4, max=20)
    private String groupName;
    @NotNull
    @Min(2)@Max(99)
    private int minAge;
    @NotNull
    @Min(2)@Max(99)
    private int maxAge;
}