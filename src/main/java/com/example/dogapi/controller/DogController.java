package com.example.dogapi.controller;

import com.example.dogapi.entity.Dog;
import com.example.dogapi.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/dog")
public class DogController {

    @Autowired
    private DogService dogService;

    @PostMapping("/add")
    public ResponseEntity<Dog> addDog (@RequestBody Dog dog) {
        return new ResponseEntity<>(dogService.addDog(dog), HttpStatus.CREATED);
    }

    @GetMapping("/get/{dogId}")
    public ResponseEntity<Dog> getDog (@PathVariable long dogId) {
        return new ResponseEntity<>(dogService.getDog(dogId),HttpStatus.OK);
    }

    @GetMapping("/get/breeds/list")
    public ResponseEntity<Map<String, Set<String>>> getAllBreedsAndSubBreeds() {
        return new ResponseEntity<>(dogService.getBreedAndSubBreed(),HttpStatus.OK);
    }

    @GetMapping("/get/breed/{breedName}/list")
    public ResponseEntity<Map<String,Set<String>>> getAllSubBreedsOfBreed(@PathVariable String breedName) {
        return new ResponseEntity<>(dogService.getAllSubBreedsFromBreed(breedName),HttpStatus.OK);
    }

}
