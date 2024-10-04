package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.api.dto.UserInfoResponse;
import io.github.eappezo.soundary.services.user.api.dto.UserUpdateRequest;
import io.github.eappezo.soundary.services.user.api.dto.UserUpdateResponse;
import io.github.eappezo.soundary.services.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(
                UserInfoResponse.from(userService.getUserInfo(userId)));
    }

    @PutMapping()
    public ResponseEntity<UserUpdateResponse> updateUserInfo(@AuthenticatedUser Identifier userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(
                UserUpdateResponse.from(userService.updateUser(userId, userUpdateRequest))
        );
    }

    @DeleteMapping()
    public ResponseEntity<Object> quitUser(@AuthenticatedUser Identifier userId) {
        userService.quitUser(userId);
        return ResponseEntity.ok().build();
    }
}
