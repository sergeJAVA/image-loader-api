package com.example.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class AwsConfiguration {

    private final BucketProperties bucketProperties;
    @Bean(name = "s3")
    @Primary
    public AmazonS3 s3() {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(bucketProperties.getAccessKeyId(),
                                bucketProperties.getSecretAccessKey())))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                bucketProperties.getStoragePath(),
                                "ru-central1"
                        )
                )
                .build();
        return s3;
    }
}
