package com.example.dogapi.controller;

import com.example.dogapi.entity.Image;
import com.example.dogapi.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

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

    @GetMapping("/get/breed/{breedName}/images")
    public ResponseEntity<List<Image>> getImagesFromBreed(@PathVariable String breedName) {
        List<Image> images = imageService.getImagesFromBreed(breedName);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }




}
