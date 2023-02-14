package com.example.dogapi.service;

import com.example.dogapi.entity.Dog;
import com.example.dogapi.entity.Image;
import com.example.dogapi.exception.DogNotFoundException;
import com.example.dogapi.repository.DogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.StampedLock;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogServiceImplTest {

    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private DogServiceImpl dogService;


    @Test
    void whenValidDog_thenSaveDog() {

        Image image = Image.builder()
                .id("1")
                .name("3.jpg")
                .type("image/jpeg")
                .filePath("D:\\demo\\dog-api\\image\\3.jpg")
                .build();

        Dog dog = Dog.builder()
                .id(1L)
                .name("thang")
                .breed("buldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HCM")
                .images(Arrays.asList(image))
                .build();

        when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        // Act
        Dog actual = dogService.addDog(new Dog());

        verify(dogRepository, times(1)).save(any(Dog.class));
        verifyNoMoreInteractions(dogRepository);

    }


    @Test
    void whenValidDogId_thenDogShouldFound() {

        Dog dog = Dog.builder()
                .id(1L)
                .name("thang")
                .breed("buldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HCM")
//                .images(Arrays.asList(image))
                .build();
        when(dogRepository.findById(anyLong())).thenReturn(Optional.ofNullable(dog));

        //Act
        Dog actual = dogService.getDog(1L);

        //Assert
        assertEquals("thang",actual.getName());
    }

    @Test
    void whenNotValidDogId_thenShouldThrowException() {

        when(dogRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Act
        assertThrows(DogNotFoundException.class, () -> dogService.getDog(anyLong()));
    }


    @Test
    void whenGetAllDog_shouldFoundDogList() {

        Dog dog1 = Dog.builder()
                .id(1L)
                .name("thang")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HCM")
//                .images(Arrays.asList(image))
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
//                .images(Arrays.asList(image))
                .build();

        List<Dog> dogList = Arrays.asList(dog1,dog2);

        when(dogRepository.findAll()).thenReturn(dogList);

        //Act

        List<Dog> actual = dogService.getAllDogs();

        verify(dogRepository,times(1)).findAll();
        verifyNoMoreInteractions(dogRepository);
        assertEquals(2,actual.size());

    }

    @Test
    void getBreedAndSubBreed() {
        //Prepare
        Dog dog1 = Dog.builder()
                .id(1L)
                .name("thang")
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HCM")
//                .images(Arrays.asList(image))
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
//                .images(Arrays.asList(image))
                .build();

        List<Dog> dogList = Arrays.asList(dog1,dog2);

        when(dogRepository.findAll()).thenReturn(dogList);

        //Act
        Map<String,Set<String>> actualMap = dogService.getBreedAndSubBreed();
        Set<String> expectSubBreed = Set.of(dog1.getSubBreed(),dog2.getSubBreed());

        //Assert
        assertEquals(expectSubBreed,actualMap.get("bulldog"));
    }

    @Test
    void getAllSubBreedsFromBreed() {

    }

    @Test
    void changeStatus() {
    }

    @Test
    void updateDog() {
    }
}