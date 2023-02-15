package com.example.dogapi.repository;

import com.example.dogapi.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,String> {
    Optional<Image> findByName(String imageName);
    Optional<Image> findByFilePath(String filePath);
    List<Image> findByDogId(long dogId);

    @Query(value = "SELECT * FROM image ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Image> findRandomImage();

    @Query(value = "SELECT * FROM image WHERE image.dog_id = :dogId", nativeQuery = true)
    List<Image> findAllImageFromDogId(@Param("dogId") long dogId);



}
