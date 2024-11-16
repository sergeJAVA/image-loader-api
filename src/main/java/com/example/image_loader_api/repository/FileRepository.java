package com.example.image_loader_api.repository;

import com.example.image_loader_api.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Image, String> {

}
