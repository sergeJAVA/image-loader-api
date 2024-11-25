package com.example.image_loader_api.controller;

import com.example.config.BucketProperties;
import com.example.image_loader_api.model.Image;
import com.example.image_loader_api.repository.FileRepository;
import com.example.image_loader_api.service.ImageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    private final FileRepository fileRepository;


    @GetMapping("/home")
    public List<Image> home() {
        return fileRepository.findAll();
    }


    @PostMapping("/upload")
    public Image uploadImage(MultipartFile file, @RequestParam String userId) {
        return imageService.uploadImage(file, userId);
    }

    @GetMapping
    public Image findImageById(String id) {
        return imageService.findImageById(id);
    }

    @DeleteMapping("/delete")
    public void deleteImage(@RequestParam String key) {
        imageService.deleteImage(key);

    }
}
