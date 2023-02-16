package com.example.dogapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DogDto {

    private long id;

    @NotBlank(message = "Breed can not be blank")
    private String breed;

    private String subBreed;

    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotNull(message = "Birth date can not be null")
    private LocalDate birthDate;

    @NotBlank(message = "Location can not be blank")
    private String location;

    @NotBlank(message = "description can not be blank")
    private String description;

    private boolean isActive;
}
