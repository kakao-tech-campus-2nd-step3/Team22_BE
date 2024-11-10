package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.services.user.api.dto.UserInfoResponse;
import io.github.eappezo.soundary.services.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController implements UserAPI {
    private final UserService userService;

    @Override
    @GetMapping
    public UserInfoResponse getUserInfo(
            @RequestParam("display-id") String displayId
    ) {
        return UserInfoResponse.from(userService.getUserInfoByDisplayId(displayId));
    }
}
