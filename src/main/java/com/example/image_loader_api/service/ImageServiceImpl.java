package com.example.image_loader_api.service;

import com.example.config.BucketProperties;
import com.example.image_loader_api.model.Image;
import com.example.image_loader_api.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final CloudService cloudService;

    private final FileRepository fileRepository;


    @SneakyThrows
    @Override
    public Image uploadImage(MultipartFile file, String userId) {
        return fileRepository.save(Image.builder()
                .downloadPath(cloudService.upload(file))
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
        String imageURL = BucketProperties.STORAGE_PATH + key;
        cloudService.deleteImage(key);
        fileRepository.deleteByDownloadPath(imageURL);
    }
}
