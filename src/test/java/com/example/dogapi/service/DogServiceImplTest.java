package com.example.dogapi.service;

import com.example.dogapi.entity.Dog;
import com.example.dogapi.entity.Image;
import com.example.dogapi.exception.DogNotFoundException;
import com.example.dogapi.repository.DogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

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
                .breed("bulldog")
                .subBreed("boston")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("HCM")
//                .images(Arrays.asList(image))
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
                .breed("bulldog")
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
    void whenGetAllDogs_shouldFoundDogList() {

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

        //Assert
        verify(dogRepository,times(1)).findAll();
        verifyNoMoreInteractions(dogRepository);
        assertEquals(2,actual.size());

    }

    @Test
    void whenHaveDogList_shouldFoundListOfAllBreedAndSubBreed() {
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

        Dog dog3 = Dog.builder()
                .id(3L)
                .name("jack")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Anh")
//                .images(Arrays.asList(image))
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

        List<Dog> dogList = Arrays.asList(dog1,dog2,dog3,dog4);

        when(dogRepository.findAll()).thenReturn(dogList);

        //Act
        Map<String,Set<String>> actualMap = dogService.getBreedAndSubBreed();
        Set<String> expectSubBreedOfBullDog = Set.of(dog1.getSubBreed(),dog2.getSubBreed());

        //Assert
        assertEquals(expectSubBreedOfBullDog,actualMap.get("bulldog"));
        assertEquals(Collections.EMPTY_SET,actualMap.get("akita"));
    }

    @Test
    void whenHasDogList_shouldFoundSubBreedListFromBreed() {
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

        Dog dog3 = Dog.builder()
                .id(3L)
                .name("jack")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Anh")
//                .images(Arrays.asList(image))
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

        List<Dog> bulldogList = Arrays.asList(dog1,dog2);
        List<Dog> akitaList = new ArrayList<>();
        akitaList.add(dog3);
        akitaList.add(dog4);

        when(dogRepository.findByBreed("bulldog")).thenReturn(bulldogList);
        when(dogRepository.findByBreed("akita")).thenReturn(akitaList);

        //Act
        Set<String> actualSubBreedListOfAkita = dogService.getAllSubBreedsFromBreed("akita");

        Set<String> actualSubBreedListOfBullDog = dogService.getAllSubBreedsFromBreed("bulldog");
        Set<String> expectSubBreedListOfBullDog = Set.of(dog1.getSubBreed(),dog2.getSubBreed());

        //Assert
        assertEquals(expectSubBreedListOfBullDog,actualSubBreedListOfBullDog);
        assertEquals(Collections.EMPTY_SET,actualSubBreedListOfAkita);
    }

    @Test
    void whenChangeStatus_shouldChangeFromTrueToFalse() {
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

        Dog modifiedDog = Dog.builder()
                .id(4L)
                .name("peter")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(false)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();

        when(dogRepository.findById(anyLong())).thenReturn(Optional.ofNullable(dog4));
        when(dogRepository.save(any(Dog.class))).thenReturn(modifiedDog);

        //Act
        Dog actualDog = dogService.changeStatus(anyLong());

        //Assert
        verify(dogRepository,times(1)).findById(anyLong());
        assertEquals(false,actualDog.isActive());
    }

    @Test
    void whenUpdateDog_shouldReturnNewUpdate() {
        Dog dog4 = Dog.builder()
                .id(4L)
                .name("wrongName")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();

        Dog modifiedDog = Dog.builder()
                .id(4L)
                .name("rightName")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();

        when(dogRepository.findById(4L)).thenReturn(Optional.ofNullable(dog4));
        when(dogRepository.save(any(Dog.class))).thenReturn(modifiedDog);

        //Act
        Dog actualDog = dogService.updateDog(4L,modifiedDog);

        //Assert
        assertEquals("rightName",actualDog.getName());
    }

    @Test
    public void whenValidDogId_thenDeleteDogSuccessfully() {
        Dog dog4 = Dog.builder()
                .id(4L)
                .name("wrongName")
                .breed("akita")
//                .subBreed("english")
                .birthDate(LocalDate.now())
                .description("tram cam")
                .isActive(true)
                .location("Nga")
//                .images(Arrays.asList(image))
                .build();
        dogService.deleteDog(dog4.getId());
        verify(dogRepository,times(1)).deleteById(dog4.getId());
    }
}