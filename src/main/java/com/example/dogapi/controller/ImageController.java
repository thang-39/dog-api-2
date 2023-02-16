package com.example.dogapi.controller;

import com.example.dogapi.entity.Image;
import com.example.dogapi.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RolesAllowed({"Admin"})
    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                                             MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> saveImage(@RequestPart("file") MultipartFile file,
                                            @RequestPart("dogId") String dogId) throws IOException {
        return new ResponseEntity<>(imageService.saveImageToFileSystem(file,dogId), HttpStatus.CREATED);
    }

    @GetMapping("/get/{fileName}")
    public ResponseEntity<?> getImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = imageService.getImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(imageData);
    }

    @GetMapping("/get/breeds/image/random")
    public ResponseEntity<?> getRandomImage() throws IOException {
        byte[] imageData = imageService.getRandomImage();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(imageData);
    }

    @GetMapping("/get/breed/{breedName}/images")
    public ResponseEntity<List<Image>> getImagesFromBreed(@PathVariable String breedName) {
        List<Image> images = imageService.getImagesFromBreed(breedName);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/get/breed/{breedName}/image/random")
    public ResponseEntity<?> getRandomImageFromBreed(@PathVariable String breedName) throws IOException {
        byte[] imageData = imageService.getRandomImageFromBreed(breedName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(imageData);
    }

    @GetMapping("/get/breed/{breedName}/images/random/{num}")
    public ResponseEntity<List<Image>> getNumOfRandomImagesFromBreed(@PathVariable String breedName,
                                                     @PathVariable long num) throws IOException {
        List<Image> imageList = imageService.getNumOfImagesFromBreed(breedName, num);
        return new ResponseEntity<>(imageList,HttpStatus.OK);
    }

    @GetMapping("/get/breed/{breedName}/{subBreed}/images")
    public ResponseEntity<List<Image>> getAllImagesFromSubBreed(@PathVariable String breedName,
                                                                @PathVariable String subBreed) {
//        List<Image> imageList;
//        if(StringUtils.isEmpty(subBreed) && subBreed.equals(" ")){
//           imageList = imageService.getImagesFromBreed(breedName);
//        }
        List<Image> imageList = imageService.getImagesFromSubBreed(breedName,subBreed);
        return new ResponseEntity<>(imageList,HttpStatus.OK);
    }

    @GetMapping("/get/breed/{breedName}/{subBreed}/image/random")
    public ResponseEntity<?> getRandomImageFromSubBreed(@PathVariable String breedName,
                                                        @PathVariable String subBreed) throws IOException {
        byte[] imageData = imageService.getRandomImageFromSubBreed(breedName,subBreed);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(imageData);
    }

    @GetMapping("/get/breed/{breedName}/{subBreed}/image/random/{num}")
    public ResponseEntity<List<Image>> getNumOfRandomImagesFromSubBreed(@PathVariable String breedName,
                                                                        @PathVariable String subBreed,
                                                                        @PathVariable long num) {
        List<Image> imageList = imageService.getNumOfRandomImagesFromSubBreed(breedName,subBreed,num);
        return new ResponseEntity<>(imageList,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{imageId}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable String imageId) {
        imageService.deleteImage(imageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/get/breed/{SubBreedAndBreed}/images/random")
//    public ResponseEntity<?> getRandomImagesFromBreedConcat(@PathVariable String subBreedAndBreed) throws IOException {
//        byte[] imageData = imageService.getRandomImageFromBreedConcat(subBreedAndBreed);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/jpeg"))
//                .body(imageData);
//    }




}
