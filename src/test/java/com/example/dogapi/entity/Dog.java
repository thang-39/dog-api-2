package com.example.dogapi.entity;

import lombok.*;

import javax.persistence.*;
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

    private String breed;

    private String subBreed;

    private int age;

    private String location;

    private String description;

    private String status;

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL)
    private List<Image> images;



}
