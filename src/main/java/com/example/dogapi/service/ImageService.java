package com.example.dogapi.service;

import com.example.dogapi.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    String saveImageToFileSystem(MultipartFile file, String dogId) throws IOException;

    byte[] getImageFromFileSystem(String imageName) throws IOException;

    List<Image> getImagesFromBreed(String breedName);
}
