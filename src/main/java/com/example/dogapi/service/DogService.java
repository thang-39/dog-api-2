package com.example.dogapi.service;

import com.example.dogapi.entity.Dog;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DogService {
    Dog addDog(Dog dog);

    Dog getDog(long dogId);

    List<Dog> getAllDogs();

    Map<String, Set<String>> getBreedAndSubBreed();

    Map<String, Set<String>> getAllSubBreedsFromBreed(String breedName);
}
