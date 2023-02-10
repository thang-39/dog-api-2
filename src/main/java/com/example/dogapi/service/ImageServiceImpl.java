package com.example.dogapi.service;

import com.example.dogapi.entity.Dog;
import com.example.dogapi.entity.Image;
import com.example.dogapi.repository.DogRepository;
import com.example.dogapi.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private DogRepository dogRepository;

    private final String FOLDER_PATH =  "D:\\demo\\dog-api\\image\\";


    @Override
    public String saveImageToFileSystem(MultipartFile file, String dogId) throws IOException {

        Long dogIdLong = Long.parseLong(dogId);

        Dog dog = dogRepository.findById(dogIdLong).get();

        String filePath = FOLDER_PATH + file.getOriginalFilename();

        Image image = imageRepository.save(Image.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .dog(dog)
                .build());

        file.transferTo(new File(filePath));

        if (image != null) {
            return "file uploaded successfully: " + filePath;
        }
        return null;
    }

    @Override
    public byte[] getImageFromFileSystem(String imageName) throws IOException {
        Optional<Image> image = imageRepository.findByName(imageName);
        String filePath = image.get().getFilePath();
        byte[] imageData = Files.readAllBytes(new File(filePath).toPath());
        return imageData;
    }

    @Override
    public List<Image> getImagesFromBreed(String breedName) {

        List<Image> imageList = new ArrayList<>();

        List<Dog> dogList = dogRepository.findByBreed(breedName);

        for (Dog dog : dogList) {
            imageList.addAll(imageRepository.findByDogId(dog.getId()));
        }

        return imageList;
    }


}
