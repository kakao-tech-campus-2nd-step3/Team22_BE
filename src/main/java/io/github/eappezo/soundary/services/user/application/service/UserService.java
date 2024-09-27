package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserInfo(Identifier userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}
