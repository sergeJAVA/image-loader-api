package com.example.image_loader_api.repository;

import com.example.image_loader_api.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<Image, Long> {
    void deleteByDownloadPath(String downloadPath);
    void deleteByUserIdAndName(String userId, String name);
    void deleteByPostId(String postId);
    Image findByUserIdAndName(String userId, String name);
    Image findByNameAndPostId(String name, String postId);
    Image findByPostId(String postId);
}
