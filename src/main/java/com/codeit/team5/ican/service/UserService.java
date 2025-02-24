package com.codeit.team5.ican.service;

import com.codeit.team5.ican.controller.dto.user.UserRegisterResponse;
import com.codeit.team5.ican.controller.dto.user.UserUpdateRequest;
import com.codeit.team5.ican.domain.User;
import com.codeit.team5.ican.exception.UserNotFoundException;
import com.codeit.team5.ican.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional
    public void register(UserRegisterResponse response) {
        User user = User.of(response);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUserId(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("유저 아이디 " + id + "를 찾을 수 없습니다.")
        );
    }

    @Transactional
    public User updateUser(Long id, MultipartFile image, UserUpdateRequest request) {
        User user = findByUserId(id);

        if(isExists(image)) {
            if(isExists(user.getProfile())) {
                s3Service.deleteImage(user.getProfile());
            }
            user.setProfile(s3Service.uploadImage(image, "users/" + user.getId(), "profile"));
        }

        if(request != null) {
            user.update(request);
        }

        user.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        return user;
    }

    private boolean isExists(MultipartFile image) {
        return image != null && !image.isEmpty();
    }

    private boolean isExists(String profile) {
        return profile != null && !profile.isEmpty();
    }

}
