package com.example.image_loader_api.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
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

    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(BucketProperties.ACCESS_KEY_ID,
                            BucketProperties.SECRET_ACCESS_KEY)))
            .withEndpointConfiguration(
                    new AmazonS3ClientBuilder.EndpointConfiguration(
                            BucketProperties.STORAGE_PATH,
                            "ru-central1"
                    )
            )
            .build();

    @SneakyThrows
    public String upload(MultipartFile file) throws Exception {
        String partName = UUID.randomUUID().toString();
        s3.putObject(new PutObjectRequest(
                BucketProperties.BUCKET_NAME,
                partName + file.getOriginalFilename(),
                file.getInputStream(),
                new ObjectMetadata()
                ));
        return BucketProperties.STORAGE_PATH + partName + file.getOriginalFilename();
    }

    public void deleteImage(String key){
        s3.deleteObject(new DeleteObjectRequest(BucketProperties.BUCKET_NAME, key));
    }
}
