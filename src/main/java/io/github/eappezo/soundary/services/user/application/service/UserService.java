package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.api.controller.dto.UserUpdateRequest;
import io.github.eappezo.soundary.services.user.application.dto.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfo getUserInfo(String userId) {
        User user = getUser(userId);
        return UserInfo.from(user);
    }

    public User updateUser(String userId, UserUpdateRequest request) {
        User user = getUser(userId);

        User updatedUser = new User(
                user.getIdentifier(),
                user.getDisplayId(),
                request.description(),
                request.nickname(),
                request.profileImageUrl(),
                user.getRoles(),
                user.getSignupAt());
        return userRepository.save(updatedUser);
    }

    public void quitUser(String userId) {
        User user = getUser(userId);
        user.getRoles().add(UserRole.LEAVED);
        userRepository.save(user);
    }

    private User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
