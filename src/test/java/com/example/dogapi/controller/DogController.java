package com.example.dogapi.controller;

import com.example.dogapi.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/dog")
public class DogController {

    @Autowired
    private DogService dogService;

    @PostMapping("/add")
    public ResponseEntity<Dog> addDog (@RequestBody Dog dog) {
        return new ResponseEntity<>(dogService.addDog(dog), HttpStatus.CREATED);
    }

}
