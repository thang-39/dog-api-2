package com.example.dogapi.service;

import com.example.dogapi.entity.Dog;
import com.example.dogapi.exception.DogNotFoundException;
import com.example.dogapi.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return unwrapDog(dogRepository.findById(dogId),dogId);
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
                    if (dog.getSubBreed() != null) {
                        breedAndSubBreed.get(breed).add(dog.getSubBreed());
                    }
                }
            }
        }
        return breedAndSubBreed;
    }

    // how to handle when have no breed
    @Override
    public Map<String,Set<String>> getAllSubBreedsFromBreed(String breedName) {

        Map<String,Set<String>> subBreedsList = new HashMap<>();
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

    @Override
    public Dog changeStatus(long dogId) {
        Dog dog = unwrapDog(dogRepository.findById(dogId),dogId);
        dog.setActive(!dog.isActive());
        dogRepository.save(dog);
        return dog;
    }

    @Override
    public Dog updateDog(long dogId, Dog dog) {
        Dog dogInRepo = unwrapDog(dogRepository.findById(dogId),dogId);

        dogInRepo.setName(dog.getName());
        dogInRepo.setBreed(dog.getBreed());
        dogInRepo.setSubBreed(dog.getSubBreed());
        dogInRepo.setLocation(dog.getLocation());
        dogInRepo.setDescription(dog.getDescription());
        dogInRepo.setBirthDate(dog.getBirthDate());
        dogInRepo.setActive(dog.isActive());

        dogInRepo = dogRepository.save(dogInRepo);
        return dogInRepo;
    }

    static Dog unwrapDog(Optional<Dog> entity, long dogId) {
        if (entity.isPresent()) return entity.get();
        else throw new DogNotFoundException(dogId);
    }


}
