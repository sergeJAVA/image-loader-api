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
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
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

    public MultipartFile download(String key) throws IOException {
        key = key.substring(key.lastIndexOf("/") + 1); // если имя файла — это конец ключа

        S3Object s3Object = s3.getObject(bucketProperties.getName(), key);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        String fileName = key;
        String contentType = s3Object.getObjectMetadata().getContentType();

        return new MockMultipartFile(
                fileName,
                fileName,
                contentType,
                inputStream
        );
    }
}
