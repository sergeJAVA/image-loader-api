package com.example.image_loader_api;

import com.example.config.BucketProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties({BucketProperties.class})
@ComponentScan(basePackages = {"com.example.config", "com.example.image_loader_api"})
public class ImageLoaderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageLoaderApiApplication.class, args);
	}

}
