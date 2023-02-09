package com.example.dogapi.service;

import com.example.dogapi.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DogServiceImpl implements DogService{

    @Autowired
    private DogRepository dogRepository;


    @Override
    public Dog addDog(Dog dog) {
        return dogRepository.save(dog);
    }
}
