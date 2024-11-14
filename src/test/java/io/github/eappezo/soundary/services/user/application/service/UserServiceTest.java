package io.github.eappezo.soundary.services.user.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    private Identifier userId;
    private User user;

    @BeforeEach
    void setUp() {
        userId = Identifier.fromString("user123");
        user = User.newUser(userId, "display123", "nickname", "description", "url");
    }

    @Test
    @DisplayName("존재하지 않는 유저 조회 시 예외 발생")
    void getUserInfo_UserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserInfo(userId));
    }

}
