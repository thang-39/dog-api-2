package com.example.dogapi.service;

import com.example.dogapi.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    String saveImageToFileSystem(MultipartFile file, String dogId) throws IOException;

    byte[] getImageFromFileSystem(String imageName) throws IOException;

    List<Image> getImagesFromBreed(String breedName);

    byte[] getRandomImage() throws IOException;

    byte[] getRandomImageFromBreed(String breedName) throws IOException;

    List<Image> getNumOfImagesFromBreed(String breedName, long num);

    List<Image> getImagesFromSubBreed(String breedName, String subBreed);

    byte[] getRandomImageFromSubBreed(String breedName, String subBreed) throws IOException;

    List<Image> getNumOfRandomImagesFromSubBreed(String breedName, String subBreed, long num);

//    byte[] getRandomImageFromBreedConcat(String subBreedAndBreed) throws IOException;
}
