package com.example.dogapi.controller;

import com.example.dogapi.entity.Dog;
import com.example.dogapi.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/dog")
public class DogController {

    @Autowired
    private DogService dogService;

    @PostMapping("/add")
    public ResponseEntity<?> addDog (@Valid @RequestBody Dog dog) {
        dogService.addDog(dog);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
    public ResponseEntity<Set<String>> getAllSubBreedsOfBreed(@PathVariable String breedName) {
        return new ResponseEntity<>(dogService.getAllSubBreedsFromBreed(breedName),HttpStatus.OK);
    }

    @PatchMapping("/edit/status/{dogId}")
    public ResponseEntity<Dog> changeStatus(@PathVariable long dogId) {
        return new ResponseEntity<>(dogService.changeStatus(dogId),HttpStatus.OK);
    }

    @PutMapping("/edit/{dogId}")
    public ResponseEntity<Dog> updateDog(@PathVariable long dogId,
                                         @Valid @RequestBody Dog dog) {
        return new ResponseEntity<>(dogService.updateDog(dogId,dog),HttpStatus.OK);
    }

}
