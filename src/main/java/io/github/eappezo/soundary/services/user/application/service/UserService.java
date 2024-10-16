package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.api.dto.UserUpdateRequest;
import io.github.eappezo.soundary.services.user.application.dto.UserInfo;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.repository.UserRoleManagerImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleManagerImpl userRoleManagerImpl;

    public UserService(UserRepository userRepository, UserRoleManagerImpl userRoleManagerImpl) {
        this.userRepository = userRepository;
        this.userRoleManagerImpl = userRoleManagerImpl;
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
        userRoleManagerImpl.appendRole(userId, UserRole.LEAVED);
    }

    private User getUser(Identifier userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
