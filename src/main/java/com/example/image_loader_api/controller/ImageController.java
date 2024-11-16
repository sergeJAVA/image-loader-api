package com.example.image_loader_api.controller;

import com.example.image_loader_api.model.Image;
import com.example.image_loader_api.repository.FileRepository;
import com.example.image_loader_api.service.ImageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Image uploadImage(String data, @RequestParam String userId) {
        return imageService.uploadImage(data, userId);
    }

    @GetMapping
    public Image findImageById(String id) {
        return imageService.findImageById(id);
    }

    @PostConstruct
    public void addImage() {
        Image image = Image.builder().data("https://www.google.com/url?sa=i&url=https%3A%2F%2Finstalook.ru%2Fblog%2Fidei-dlya-fotosessii-na-more&psig=AOvVaw1ZaWVSWtykgH8w7OEak4Ea&ust=1731395435495000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCNCbuODc04kDFQAAAAAdAAAAABAE").userId("21").build();
        fileRepository.save(image);
    }
}
