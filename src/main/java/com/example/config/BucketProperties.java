package com.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "bucket.properties")
public class BucketProperties {

    private String name;

    private String accessKeyId;

    private String secretAccessKey;

    private String storagePath;


}
