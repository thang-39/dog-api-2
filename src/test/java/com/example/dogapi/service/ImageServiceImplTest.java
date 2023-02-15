package com.example.dogapi.service;

import com.example.dogapi.entity.Dog;
import com.example.dogapi.entity.Image;
import com.example.dogapi.exception.DogNotFoundException;
import com.example.dogapi.exception.ImageNotFoundException;
import com.example.dogapi.repository.DogRepository;
import com.example.dogapi.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    private final byte[] data = new byte[]{1,2,3};

    private final MockMultipartFile file = new MockMultipartFile(
            "file",
            "3.jpg",
            "image/jpeg",
            data);

    @Test
    void saveImageToFileSystem() throws IOException {
        Dog dog = Dog.builder()
                .id(1L)
                .name("thang")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HCM")
                .build();
        when(dogRepository.findById(anyLong())).thenReturn(Optional.ofNullable(dog));

        Image image = Image.builder()
                .id("1")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("D:\\demo\\dog-api\\image\\3.jpg")
                .dog(dog)
                .build();
        String returnString = "file uploaded successfully: " + image.getFilePath();
        when(imageRepository.save(any(Image.class))).thenReturn(image);

        //Act
        String actualReturn = imageService.saveImageToFileSystem(file,"1");

        //Assert
        assertEquals(returnString,actualReturn);

    }

    @Test
    void getImageFromFileSystem() throws IOException {
        Dog dog = Dog.builder()
                .id(1L)
                .name("thang")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HCM")
                .build();

        Image image = Image.builder()
                .id("1")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/3.jpg")
                .dog(dog)
                .build();

        when(imageRepository.findByName(anyString())).thenReturn(Optional.ofNullable(image));

        byte [] imageData = imageService.getImageFromFileSystem(image.getName());

        assertEquals(imageData.length,0);

        //Act

    }

    @Test
    void getRandomImage() throws IOException {
        Dog dog = Dog.builder()
                .id(1L)
                .name("thang")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HCM")
                .build();

        Image image = Image.builder()
                .id("1")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/3.jpg")
                .dog(dog)
                .build();

        when(imageRepository.findRandomImage()).thenReturn(Optional.ofNullable(image));

        byte [] imageData = imageService.getRandomImage();

        assertEquals(imageData.length,0);
    }

    @Test
    void whenNoImageInDB_thenThrowImageNotFoundException() throws IOException {
        when(imageRepository.findRandomImage()).thenReturn(Optional.empty());
        assertThrows(ImageNotFoundException.class, () -> imageService.getRandomImage());

    }

    @Test
    void getImagesFromBreed() {
        Dog dog1 = Dog.builder()
                .id(1L)
                .name("thang")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HCM")
                .build();
        Image image1 = Image.builder()
                .id("1")
                .name("1.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/1.jpg")
                .dog(dog1)
                .build();

        Dog dog2 = Dog.builder()
                .id(2L)
                .name("lex")
                .breed("bulldog")
                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HN")
                .build();
        Image image2 = Image.builder()
                .id("2")
                .name("2.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/2.jpg")
                .dog(dog2)
                .build();

        Dog dog3 = Dog.builder()
                .id(3L)
                .name("jack")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Anh")
                .build();
        Image image3 = Image.builder()
                .id("3")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/3.jpg")
                .dog(dog3)
                .build();

        Dog dog4 = Dog.builder()
                .id(4L)
                .name("peter")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();
        Image image4 = Image.builder()
                .id("4")
                .name("4.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/4.jpg")
                .dog(dog4)
                .build();
        Image image5 = Image.builder()
                .id("5")
                .name("5.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/5.jpg")
                .dog(dog4)
                .build();

        List<Dog> akitaDogList = Arrays.asList(dog3,dog4);

        when(dogRepository.findByBreed("akita")).thenReturn(akitaDogList);
        when(imageRepository.findByDogId(dog3.getId())).thenReturn(Arrays.asList(image3));
        when(imageRepository.findByDogId(dog4.getId())).thenReturn(Arrays.asList(image4,image5));

        List<Image> actualImages = imageService.getImagesFromBreed("akita");
        assertEquals(3,actualImages.size());

    }

    @Test
    void getRandomImageFromBreed() throws IOException {
        Dog dog3 = Dog.builder()
                .id(3L)
                .name("jack")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Anh")
                .build();
        Image image3 = Image.builder()
                .id("3")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/3.jpg")
                .dog(dog3)
                .build();

        Dog dog4 = Dog.builder()
                .id(4L)
                .name("peter")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();
        Image image4 = Image.builder()
                .id("4")
                .name("4.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/4.jpg")
                .dog(dog4)
                .build();
        Image image5 = Image.builder()
                .id("5")
                .name("5.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/5.jpg")
                .dog(dog4)
                .build();

        List<Dog> akitaDogList = Arrays.asList(dog3,dog4);
        when(dogRepository.findByBreed("akita")).thenReturn(akitaDogList);
        when(imageRepository.findByDogId(dog3.getId())).thenReturn(Collections.singletonList(image3));
        when(imageRepository.findByDogId(dog4.getId())).thenReturn(Arrays.asList(image4,image5));

        //Act
        byte[] imageData = imageService.getRandomImageFromBreed("akita");

        verify(imageRepository,times(2)).findByDogId(anyLong());
        assertEquals(0,imageData.length);
    }

    @Test
    void getNumOfImagesFromBreed() {
        Dog dog3 = Dog.builder()
                .id(3L)
                .name("jack")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Anh")
                .build();
        Image image3 = Image.builder()
                .id("3")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/3.jpg")
                .dog(dog3)
                .build();

        Dog dog4 = Dog.builder()
                .id(4L)
                .name("peter")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();
        Image image4 = Image.builder()
                .id("4")
                .name("4.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/4.jpg")
                .dog(dog4)
                .build();
        Image image5 = Image.builder()
                .id("5")
                .name("5.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/5.jpg")
                .dog(dog4)
                .build();

        List<Dog> akitaDogList = Arrays.asList(dog3,dog4);
        when(dogRepository.findByBreed("akita")).thenReturn(akitaDogList);
        when(imageRepository.findByDogId(dog3.getId())).thenReturn(Collections.singletonList(image3));
        when(imageRepository.findByDogId(dog4.getId())).thenReturn(Arrays.asList(image4,image5));

        //Act
        List<Image> actualImageList = imageService.getNumOfImagesFromBreed("akita",2L);

        assertEquals(2,actualImageList.size());
    }

    @Test
    void getImagesFromSubBreed() {
        Dog dog3 = Dog.builder()
                .id(3L)
                .name("ted")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Anh")
                .build();
        Image image3 = Image.builder()
                .id("3")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/3.jpg")
                .dog(dog3)
                .build();

        Dog dog4 = Dog.builder()
                .id(4L)
                .name("lex")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();
        Image image4 = Image.builder()
                .id("4")
                .name("4.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/4.jpg")
                .dog(dog4)
                .build();
        Image image5 = Image.builder()
                .id("5")
                .name("5.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/5.jpg")
                .dog(dog4)
                .build();

        List<Dog> bostonBulldogList = Arrays.asList(dog3,dog4);
        when(dogRepository.findByBreedAndSubBreed("bulldog","boston")).thenReturn(bostonBulldogList);
        when(imageRepository.findByDogId(dog3.getId())).thenReturn(Collections.singletonList(image3));
        when(imageRepository.findByDogId(dog4.getId())).thenReturn(Arrays.asList(image4,image5));

        //Act
        List<Image> actualImageList = imageService.getImagesFromSubBreed("bulldog","boston");

        //Assert
        assertEquals(3, actualImageList.size());
    }

    @Test
    void getRandomImageFromSubBreed() throws IOException {
        Dog dog3 = Dog.builder()
                .id(3L)
                .name("ted")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Anh")
                .build();
        Image image3 = Image.builder()
                .id("3")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/3.jpg")
                .dog(dog3)
                .build();

        Dog dog4 = Dog.builder()
                .id(4L)
                .name("lex")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();
        Image image4 = Image.builder()
                .id("4")
                .name("4.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/4.jpg")
                .dog(dog4)
                .build();
        Image image5 = Image.builder()
                .id("5")
                .name("5.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/5.jpg")
                .dog(dog4)
                .build();

        List<Dog> bostonBulldogList = Arrays.asList(dog3,dog4);
        when(dogRepository.findByBreedAndSubBreed("bulldog","boston")).thenReturn(bostonBulldogList);
        when(imageRepository.findByDogId(dog3.getId())).thenReturn(Collections.singletonList(image3));
        when(imageRepository.findByDogId(dog4.getId())).thenReturn(Arrays.asList(image4,image5));

        //Act
        byte[] actualImageData = imageService.getRandomImageFromSubBreed("bulldog","boston");

        //Assert
        assertEquals(0,actualImageData.length);
    }

    @Test
    void getNumOfRandomImagesFromSubBreed() {
        Dog dog3 = Dog.builder()
                .id(3L)
                .name("ted")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Anh")
                .build();
        Image image3 = Image.builder()
                .id("3")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/3.jpg")
                .dog(dog3)
                .build();

        Dog dog4 = Dog.builder()
                .id(4L)
                .name("lex")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();
        Image image4 = Image.builder()
                .id("4")
                .name("4.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/4.jpg")
                .dog(dog4)
                .build();
        Image image5 = Image.builder()
                .id("5")
                .name("5.jpg")
                .type("image/jpeg")
                .filePath("./src/main/resources/static/image/5.jpg")
                .dog(dog4)
                .build();

        List<Dog> bostonBulldogList = Arrays.asList(dog3,dog4);
        when(dogRepository.findByBreedAndSubBreed("bulldog","boston")).thenReturn(bostonBulldogList);
        when(imageRepository.findByDogId(dog3.getId())).thenReturn(Collections.singletonList(image3));
        when(imageRepository.findByDogId(dog4.getId())).thenReturn(Arrays.asList(image4,image5));

        //Act
        List<Image> actualImageList = imageService.getNumOfRandomImagesFromSubBreed("bulldog","boston",1);

        //Assert
        assertEquals(1,actualImageList.size());
    }
}