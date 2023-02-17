package com.example.dogapi.controller;

import com.example.dogapi.service.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class S3StorageController {

    @Autowired
    private S3StorageService s3StorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(s3StorageService.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = s3StorageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type","application/octet-stream")
                .header("Content-disposition","attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping("/getUrl/{fileName}")
    public ResponseEntity<String> getUrlFile(@PathVariable String fileName) {

        return new ResponseEntity<>(s3StorageService.getUrlOfFile(fileName),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(s3StorageService.deleteFile(fileName),HttpStatus.NO_CONTENT);
    }

}
