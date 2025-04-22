package com.example.image_loader_api.service.Avatar;

import com.example.image_loader_api.model.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    Avatar findById(Long id);
    void deleteById(Long id);
    Avatar save(String userId, MultipartFile file) throws Exception;
    Avatar update(String userId, Avatar updatedAvatar);
    Avatar findByUserId(String userId);
    MultipartFile getAvatarByUserId(String userId) throws IOException;
}
