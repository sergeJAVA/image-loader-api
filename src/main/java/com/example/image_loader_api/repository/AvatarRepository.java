package com.example.image_loader_api.repository;

import com.example.image_loader_api.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByUserId(String userId);
    void deleteAllByUserId(String userId);
    List<Avatar> findAllByUserId(String userId);
}
