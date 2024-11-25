package com.example.image_loader_api.service;

import com.example.image_loader_api.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image uploadImage(MultipartFile file, String userId);

    Image findImageById(String id);

    void deleteImage(String key);
}
