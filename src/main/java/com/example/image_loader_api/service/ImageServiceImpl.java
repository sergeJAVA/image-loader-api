package com.example.image_loader_api.service;

import com.example.image_loader_api.model.Image;
import com.example.image_loader_api.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Autowired
    private final FileRepository fileRepository;


    @Override
    public Image uploadImage(String data, String userId) {

        try {
            fileRepository.save(Image.builder()
                    .data(data)
                    .userId(userId)
                    .build());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Image findImageById(String id) {
        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }
}
