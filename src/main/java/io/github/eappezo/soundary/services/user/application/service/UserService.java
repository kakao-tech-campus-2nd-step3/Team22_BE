package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.UserDeviceRepository;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.core.user.UserRole;

import io.github.eappezo.soundary.services.user.application.LabelRepository;
import io.github.eappezo.soundary.services.user.application.UserRoleManager;
import io.github.eappezo.soundary.services.user.application.dto.UserInfo;
import io.github.eappezo.soundary.services.user.application.dto.UserPatch;
import io.github.eappezo.soundary.services.user.domain.exception.AlreadyInitializedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleManager userRoleManager;
    private final LabelRepository labelRepository;
    private final UserDeviceRepository userDeviceRepository;

    @Transactional(readOnly = true)
    public UserInfo getUserInfo(Identifier userId) {
        User user = getUser(userId);
        return UserInfo.from(user);
    }

    @Transactional
    public void initializeUser(
            Identifier userId,
            String userDeviceToken,
            List<Label> labels,
            UserPatch patch
    ) {
        if (!userRoleManager.hasRole(userId, UserRole.PENDING)) {
            throw new AlreadyInitializedUserException();
        }
        labelRepository.saveAll(userId, labels);
        userRoleManager.removeRole(userId, UserRole.PENDING);
        userRoleManager.appendRole(userId, UserRole.USER);

        User updatedUser = patch.applyToUser(getUser(userId));
        userRepository.save(updatedUser);
        userDeviceRepository.registerDevice(userId, userDeviceToken);
    }

    @Transactional
    public UserInfo updateUser(Identifier userId, UserPatch patch) {
        User updatedUser = patch.applyToUser(getUser(userId));

        userRepository.save(updatedUser);
        return UserInfo.from(updatedUser);
    }

    @Transactional
    public void quitUser(Identifier userId) {
        userRoleManager.appendRole(userId, UserRole.LEAVED);
    }

    public User getUser(Identifier userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
