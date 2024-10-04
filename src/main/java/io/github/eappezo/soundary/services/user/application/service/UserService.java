package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.application.dto.UserInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    // 이전에 짜둔거라 수정해야함 identifier 사용하는거 아직 제대로 안 봄
    public User updateUser(String userId, UserUpdateRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            User updatedUser = new User(
                    user.getIdentifier(),
                    user.getDisplayId(),
                    request.description(),
                    request.nickname(),
                    request.profileImageUrl(),
                    user.getRoles(),
                    user.getSignupAt()
            );
            return userRepository.save(updatedUser);
        }
        return null;
    }

    public String deleteUser(String userId) {
        User user = getUser(userId);
        user.getRoles().add(UserRole.LEAVED);
        userRepository.save(user);
        return "User deleted";
    }

    private User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
