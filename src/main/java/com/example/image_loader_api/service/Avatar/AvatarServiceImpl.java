package com.example.image_loader_api.service.Avatar;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.example.image_loader_api.model.Avatar;
import com.example.image_loader_api.repository.AvatarRepository;
import com.example.image_loader_api.service.CloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final CloudService cloudService;

    @Override
    public Avatar findById(Long id) {
        return avatarRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        avatarRepository.deleteById(id);
    }

    @Override
    public Avatar save(String userId, MultipartFile file) throws Exception {
        String downloadPath = cloudService.upload(file);
        Avatar avatar = Avatar.builder()
                .userId(userId)
                .downloadPath(downloadPath)
                .build();
        return avatarRepository.save(avatar);
    }

    @Override
    public Avatar update(String userId, Avatar updatedAvatar) {
        Optional<Avatar> oldAvatar = avatarRepository.findByUserId(userId);
        if (oldAvatar.isPresent()) {
            oldAvatar.get().setDownloadPath(updatedAvatar.getDownloadPath());
            return avatarRepository.save(oldAvatar.get());
        }
        return null;
    }

    @Override
    public Avatar findByUserId(String userId) {
        return avatarRepository.findByUserId(userId).orElse(null);
    }

    @Override
    public MultipartFile getAvatarByUserId(String userId) throws IOException {
        Optional<Avatar> avatar = avatarRepository.findByUserId(userId);
        if (avatar.isPresent()) {
            return cloudService.download(avatar.get().getDownloadPath());
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteAllByUserId(String userId) {
        List<Avatar> avatars = avatarRepository.findAllByUserId(userId);
        if (!avatars.isEmpty()) {
            avatars.forEach(avatar -> {
                String key = avatar.getDownloadPath();
                key = key.substring(key.lastIndexOf("/") + 1);
                cloudService.deleteImage(key);
            });
        }
        avatarRepository.deleteAllByUserId(userId);
    }
}
