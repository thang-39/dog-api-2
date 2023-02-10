package com.example.dogapi.repository;

import com.example.dogapi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {
    Optional<Image> findByName(String imageName);
    List<Image> findByDogId(long dogId);

}
