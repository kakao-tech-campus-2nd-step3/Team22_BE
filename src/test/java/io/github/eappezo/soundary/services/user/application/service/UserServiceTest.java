package io.github.eappezo.soundary.services.user.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.github.eappezo.soundary.core.exception.common.AlreadyExistsUserException;
import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.UserDeviceRepository;
import io.github.eappezo.soundary.core.user.*;
import io.github.eappezo.soundary.services.user.application.LabelRepository;
import io.github.eappezo.soundary.services.user.application.LeavedUserRepository;
import io.github.eappezo.soundary.services.user.application.dto.UserPatch;
import io.github.eappezo.soundary.services.user.domain.exception.AlreadyInitializedUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleManager userRoleManager;
    @Mock
    private LabelRepository labelRepository;
    @Mock
    private UserDeviceRepository userDeviceRepository;
    @Mock
    private LeavedUserRepository leavedUserRepository;

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

    @Test
    @DisplayName("중복된 displayId로 사용자 초기화 시 예외 발생")
    void initializeUser_AlreadyExistsUser() {
        UserPatch patch = new UserPatch("existingId", "newNick", "newDesc", "newUrl");
        when(userRepository.existsByDisplayId(patch.displayId())).thenReturn(true);

        assertThrows(AlreadyExistsUserException.class, () ->
                userService.initializeUser(userId, "token", List.of(Label.CLASSIC), patch));
    }

    @Test
    @DisplayName("이미 초기화된 사용자에 대해 초기화 시도 시 예외 발생")
    void initializeUser_AlreadyInitializedUser() {
        UserPatch patch = new UserPatch("newId", "newNick", "newDesc", "newUrl");
        when(userRoleManager.hasRole(userId, UserRole.PENDING)).thenReturn(false);

        assertThrows(AlreadyInitializedUserException.class, () ->
                userService.initializeUser(userId, "token", List.of(Label.CLASSIC), patch));
    }

    @Test
    @DisplayName("유저 정보를 업데이트")
    void updateUser_Success() {
        UserPatch patch = new UserPatch("updatedId", "updatedNick", "updatedDesc", "updatedUrl");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(labelRepository.findAllByUserId(userId)).thenReturn(List.of(Label.CLASSIC));

        userService.updateUser(userId, patch);

        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("유저 탈퇴 처리")
    void quitUser_Success() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.quitUser(userId);

        verify(userRoleManager).appendRole(userId, UserRole.LEAVED);
        verify(userRepository).deleteById(userId);
        verify(leavedUserRepository).save(user);
    }


    @Test
    @DisplayName("유저의 기기 토큰 업데이트")
    void updateUserDeviceToken_Success() {
        userService.updateUserDeviceToken(userId, "newDeviceToken");

        verify(userDeviceRepository).removeAllDevicesByUserId(userId);
        verify(userDeviceRepository).registerDevice(userId, "newDeviceToken");
    }

}
