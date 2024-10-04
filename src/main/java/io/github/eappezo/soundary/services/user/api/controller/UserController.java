package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.api.controller.dto.UserInfoResponse;
import io.github.eappezo.soundary.services.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(
                UserInfoResponse.from(userService.getUserInfo(userId.toString())));
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteUser(@AuthenticatedUser Identifier userId) {
        return ResponseEntity.ok(userService.deleteUser(userId.toString()));
    }
}
