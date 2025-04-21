package com.example.image_loader_api.service;

import com.example.config.BucketProperties;
import com.example.image_loader_api.model.Image;
import com.example.image_loader_api.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final CloudService cloudService;
    private final BucketProperties bucketProperties;
    private final FileRepository fileRepository;


    @SneakyThrows
    @Override
    public Image uploadImage(MultipartFile file, String userId, String name) {
        return fileRepository.save(Image.builder()
                .downloadPath(cloudService.upload(file))
                .name(name)
                .userId(userId)
                .build());
    }

    @Override
    public Image findImageById(String id) {
        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }

    @Override
    @Transactional
    public void deleteImage(String key) {
        String imageURL = bucketProperties.getStoragePath() + key;
        cloudService.deleteImage(key);
        fileRepository.deleteByDownloadPath(imageURL);
    }

    @Override
    @Transactional
    public void deleteImageByUserIdAndName(String userId, String name) {
        Image image = fileRepository.findByUserIdAndName(userId, name);
        String key = image.getDownloadPath().substring(bucketProperties.getStoragePath().length());
        fileRepository.deleteByUserIdAndName(userId, name);
        cloudService.deleteImage(key);
    }

    public MultipartFile getImageByUserIdAndName(String userId, String name) throws IOException {
        Image image = fileRepository.findByUserIdAndName(userId, name);
        if (Optional.ofNullable(image).isPresent()) {
            return cloudService.download(image.getDownloadPath());
        }
        return null;
    }
}
