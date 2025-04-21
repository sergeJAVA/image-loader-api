package com.example.image_loader_api.controller;

import com.example.image_loader_api.model.Image;
import com.example.image_loader_api.repository.FileRepository;
import com.example.image_loader_api.service.ImageService;
import com.example.image_loader_api.service.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    private final FileRepository fileRepository;
    private final JWTService jwtService;


    @GetMapping("/home")
    public List<Image> home() {
        return fileRepository.findAll();
    }


    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Image uploadImage(@RequestBody MultipartFile file, @RequestParam String userId, @RequestParam String title) {
        return imageService.uploadImage(file, userId, title);
    }

    @GetMapping("/download/{name}")
    public ResponseEntity<byte[]> downloadFile(@CookieValue("token") String token, @PathVariable String name) {
        try {
            MultipartFile file = imageService.getImageByUserIdAndName(jwtService.getUserIdFromToken(token).toString(), name);
            if (file == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public Image findImageById(String id) {
        return imageService.findImageById(id);
    }

    @DeleteMapping("/delete")
    public void deleteImage(@RequestParam String key) {
        imageService.deleteImage(key);
    }

    @DeleteMapping("/delete/image")
    public void deleteImage(@RequestParam("userId") String userId,
                            @RequestParam("name") String name) {
        imageService.deleteImageByUserIdAndName(userId, name);
    }
}
