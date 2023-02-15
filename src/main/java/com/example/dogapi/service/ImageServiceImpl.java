package com.example.dogapi.service;

import com.example.dogapi.entity.Dog;
import com.example.dogapi.entity.Image;
import com.example.dogapi.exception.ImageNotFoundException;
import com.example.dogapi.repository.DogRepository;
import com.example.dogapi.repository.ImageRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private DogRepository dogRepository;

    private final String FOLDER_PATH =  "D:\\demo\\dog-api\\image\\";


    @Override
    public String saveImageToFileSystem(MultipartFile file, String dogId) throws IOException {

        long dogIdLong = Long.parseLong(dogId);

        Dog dog = DogServiceImpl.unwrapDog(dogRepository.findById(dogIdLong),dogIdLong);

        String filePath = FOLDER_PATH + file.getOriginalFilename();

        Image image = imageRepository.save(Image.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .dog(dog)
                .build());
        file.transferTo(new File(filePath));
        return "file uploaded successfully: " + filePath;
    }

    @Override
    public byte[] getImageFromFileSystem(String imageName) throws IOException {
        Image image = unwrapImage(imageRepository.findByName(imageName),imageName);
        return getByteDataFromImage(image);
    }

    @Override
    public byte[] getRandomImage() throws IOException {
        Image image = unwrapImage(imageRepository.findRandomImage(),"Not Found");
        return getByteDataFromImage(image);
    }

    @Override
    public List<Image> getImagesFromBreed(String breedName) {
        List<Dog> dogList = dogRepository.findByBreed(breedName);
        return getImagesFromDogList(dogList);
    }

    @Override
    public byte[] getRandomImageFromBreed(String breedName) throws IOException {
//        List<Dog> dogList = dogRepository.findByBreed(breedName);
//
//        List<List<Image>> listOfImageList = dogList.stream()
//                .map(Dog::getId)
//                .map(id -> imageRepository.findAllImageFromDogId(id))
//                .toList();
//
//        List<Image> imageList = new ArrayList<>();
//
//        for (List<Image> images : listOfImageList) {
//            imageList.addAll(images);
//        }

        List<Image> imageList = getImagesFromBreed(breedName);
        Image randomImage = getRandomImage(imageList);
        return getByteDataFromImage(randomImage);

    }

    @Override
    public List<Image> getNumOfImagesFromBreed(String breedName, long num) {
        List<Image> imageList = getImagesFromBreed(breedName);
        return getRandomImages(imageList,num);
    }

    @Override
    public List<Image> getImagesFromSubBreed(String breedName, String subBreed) {
        List<Dog> dogList = dogRepository.findByBreedAndSubBreed(breedName,subBreed);
        return getImagesFromDogList(dogList);
    }

    @Override
    public byte[] getRandomImageFromSubBreed(String breedName, String subBreed) throws IOException {
        List<Dog> dogList = dogRepository.findByBreedAndSubBreed(breedName,subBreed);
        List<Image> imageList = getImagesFromDogList(dogList);
        Image randomImage = getRandomImage(imageList);
        return getByteDataFromImage(randomImage);
    }

    @Override
    public List<Image> getNumOfRandomImagesFromSubBreed(String breedName, String subBreed, long num) {
        List<Dog> dogList = dogRepository.findByBreedAndSubBreed(breedName,subBreed);
        List<Image> imageList = getImagesFromDogList(dogList);
        return getRandomImages(imageList,num);
    }

//    @Override
//    public byte[] getRandomImageFromBreedConcat(String subBreedAndBreed) throws IOException {
//        String[] subBreedAndBreedSplit = subBreedAndBreed.split(" ");
//
//        if (subBreedAndBreedSplit.length > 1) {
//            String subBreed = subBreedAndBreedSplit[0];
//            String breed = subBreedAndBreedSplit[1];
//            return getRandomImageFromSubBreed(breed,subBreed);
//        } else {
//            String breed = subBreedAndBreedSplit[0];
//            return getRandomImageFromBreed(breed);
//        }
//    }

    private List<Image> getRandomImages(List<Image> imageList,
                                        long num) {
        Random rand = new Random();
        List<Image> randomImageList = new ArrayList<>();

        for (long i = 0; i < num; i++) {
            int randomIndex = rand.nextInt(imageList.size());
            randomImageList.add(imageList.get(randomIndex));
            imageList.remove(randomIndex);
        }
        return randomImageList;
    }

    private Image getRandomImage(List<Image> imageList) {
        Random rand = new Random();
        return imageList.get(rand.nextInt(imageList.size()));
    }

    private byte[] getByteDataFromImage(Image image) throws IOException {
        String filePath = image.getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    private List<Image> getImagesFromDogList(List<Dog> dogList) {
        List<Image> imageList = new ArrayList<>();
        for (Dog dog : dogList) {
            imageList.addAll(imageRepository.findByDogId(dog.getId()));
        }
        return imageList;
    }

    static Image unwrapImage(Optional<Image> entity, String id) {
        if (entity.isPresent()) return entity.get();
        else throw new ImageNotFoundException(id);
    }


}
