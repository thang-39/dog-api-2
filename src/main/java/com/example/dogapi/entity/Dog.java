package com.example.dogapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Breed can not be blank")
    private String breed;

    private String subBreed;

    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotNull(message = "Birth date can not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @NotBlank(message = "Location can not be blank")
    private String location;

    @NotBlank(message = "description can not be blank")
    private String description;


    private boolean isActive = true;

    @JsonIgnore
    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL)
    private List<Image> images;



}
