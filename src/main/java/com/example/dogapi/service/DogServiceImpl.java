package com.example.dogapi.service;

import com.example.dogapi.entity.Dog;
import com.example.dogapi.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DogServiceImpl implements DogService {

    @Autowired
    private DogRepository dogRepository;

    @Override
    public Dog addDog(Dog dog) {
        return dogRepository.save(dog);
    }

    @Override
    public Dog getDog(long dogId) {
        return dogRepository.findById(dogId).get();
    }


    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    private Set<String> getBreedList(List<Dog> dogList) {
        Set<String> breedList = new HashSet<>();
        for (Dog dog : dogList) {
            breedList.add(dog.getBreed());
        }
        return breedList;
    }

    @Override
    public Map<String, Set<String>> getBreedAndSubBreed() {

        List<Dog> dogList = getAllDogs();

        Set<String> breedList = getBreedList(dogList);

        Map<String, Set<String>> breedAndSubBreed = new HashMap<>();

        for (String breed : breedList) {
            breedAndSubBreed.put(breed, new HashSet<>());
            for (Dog dog : dogList) {
                if (dog.getBreed().equals(breed)) {
                    breedAndSubBreed.get(breed).add(dog.getSubBreed());
                }
            }
        }
        return breedAndSubBreed;
    }

    // how to handle when have no breed
    @Override
    public Map<String, Set<String>> getAllSubBreedsFromBreed(String breedName) {

        Map<String, Set<String>> subBreedsList = new HashMap<>();
        subBreedsList.put(breedName,new HashSet<>());

        List<Dog> dogList = dogRepository.findByBreed(breedName);

        if (dogList.isEmpty()) {
            subBreedsList.get(breedName).add("Breed is not available");
            return subBreedsList;
        }

        for (Dog dog : dogList) {
            if (dog.getBreed().equals(breedName)) {
                subBreedsList.get(breedName).add(dog.getSubBreed());
            }
        }

        return subBreedsList;
    }


}
