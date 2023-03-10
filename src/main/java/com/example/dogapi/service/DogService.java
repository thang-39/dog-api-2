package com.example.dogapi.service;

import com.example.dogapi.entity.Dog;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DogService {
    Dog addDog(Dog dog);

    Dog getDog(long dogId);

    List<Dog> getAllDogs();

    Map<String, Set<String>> getBreedAndSubBreed();

    Set<String> getAllSubBreedsFromBreed(String breedName);

    Dog changeStatus(long dogId);

    Dog updateDog(long dogId, Dog dog);

    void deleteDog(long dogId);
}
