package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.api.dto.UserUpdateRequest;
import io.github.eappezo.soundary.services.user.application.dto.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfo getUserInfo(Identifier userId) {
        User user = getUser(userId);
        return UserInfo.from(user);
    }

    public UserInfo updateUser(Identifier userId, UserUpdateRequest request) {
        User user = getUser(userId);
        User updateduser = user.updtaeUserInfo(request.nickname(), request.description(), request.profileImageUrl());
        userRepository.save(updateduser);
        return UserInfo.from(updateduser);
    }


    public void quitUser(Identifier userId) {
        User user = getUser(userId);
        user.getRoles().add(UserRole.LEAVED);
        userRepository.save(user);
    }

    public User getUser(Identifier userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
