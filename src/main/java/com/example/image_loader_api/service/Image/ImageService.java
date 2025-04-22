package com.example.image_loader_api.service.Image;

import com.example.image_loader_api.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(MultipartFile file, String userId, String name, String postId);

    Image findImageById(Long id);

    void deleteImage(String key);
    void deleteImageByUserIdAndName(String userId, String name);
    MultipartFile getImageByUserIdAndName(String userId, String name) throws IOException;
    MultipartFile getImageByNameAndPostId(String name, String postId) throws IOException;
}
