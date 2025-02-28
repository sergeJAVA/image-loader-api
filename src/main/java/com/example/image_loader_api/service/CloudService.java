package com.example.image_loader_api.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.config.BucketProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudService {

    private final BucketProperties bucketProperties;

    private final AmazonS3 s3;

    @SneakyThrows
    public String upload(MultipartFile file) throws Exception {
        String partName = UUID.randomUUID().toString();
        s3.putObject(new PutObjectRequest(
                bucketProperties.getName(),
                partName + file.getOriginalFilename(),
                file.getInputStream(),
                new ObjectMetadata()
                ));
        return bucketProperties.getStoragePath() + partName + file.getOriginalFilename();
    }

    public void deleteImage(String key){
        s3.deleteObject(new DeleteObjectRequest(bucketProperties.getName(), key));
    }
}
