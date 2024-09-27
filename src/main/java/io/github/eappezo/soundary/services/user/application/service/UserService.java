package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.core.user.UserRole;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // dto 수정해야함 짜놓고 가져오질 않음
    public User getUserInfo(Identifier userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    // 이전에 짜둔거라 수정해야함 identifier 사용하는거 아직 제대로 안 봄
    public User updateUser(Identifier userId, UserUpdateRequest request) {
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

    public void deleteUser(Identifier userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getRoles().add(UserRole.LEAVED);
        }
    }
}
