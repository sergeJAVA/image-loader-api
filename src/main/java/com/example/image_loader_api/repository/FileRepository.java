package com.example.image_loader_api.repository;

import com.example.image_loader_api.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<Image, Long> {
}
