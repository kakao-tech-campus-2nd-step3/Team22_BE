package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.api.dto.*;
import io.github.eappezo.soundary.services.user.application.dto.UserInfo;
import io.github.eappezo.soundary.services.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class MeController implements MeAPI {
    private final UserService userService;

    @Override
    @GetMapping
    public UserInfoResponse getMyInfo(@AuthenticatedUser Identifier userId) {
        return UserInfoResponse.from(userService.getUserInfo(userId));
    }

    @Override
    @PostMapping("/default-info")
    public void initializeUser(
            @AuthenticatedUser Identifier userId,
            @RequestBody UserInfoInitializeRequest request
    ) {
        userService.initializeUser(
                userId,
                request.deviceToken(),
                request.labels(),
                request.extractUserPatch()
        );
    }

    @Override
    @PutMapping
    public UserUpdateResponse updateMyInfo(
            @AuthenticatedUser Identifier userId,
            @RequestBody UserUpdateRequest request
    ) {
        UserInfo userInfo = userService.updateUser(userId, request.toUserPatch());

        return UserUpdateResponse.from(userInfo);
    }

    @Override
    @PatchMapping("/device-token")
    public void updateDeviceToken(
            @AuthenticatedUser Identifier userId,
            @RequestBody UpdateDeviceRequest request
    ) {
        userService.updateUserDeviceToken(userId, request.deviceToken());
    }

    @Override
    @DeleteMapping
    public void quit(@AuthenticatedUser Identifier userId) {
        userService.quitUser(userId);
    }
}
