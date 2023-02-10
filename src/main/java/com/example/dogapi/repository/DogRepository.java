package com.example.dogapi.repository;

import com.example.dogapi.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog,Long> {
    List<Dog> findByBreed(String breedName);
}
