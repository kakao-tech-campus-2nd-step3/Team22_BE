package io.github.eappezo.soundary.services.authentication.api.controller;

import io.github.eappezo.soundary.services.authentication.api.dto.request.LoginRequest;
import io.github.eappezo.soundary.services.authentication.api.dto.response.LoginResponse;
import io.github.eappezo.soundary.services.authentication.api.dto.request.RefreshRequest;
import io.github.eappezo.soundary.services.authentication.api.dto.response.RefreshResponse;
import io.github.eappezo.soundary.services.authentication.application.LoginResultDto;
import io.github.eappezo.soundary.services.authentication.application.RefreshResultDto;
import io.github.eappezo.soundary.services.authentication.application.service.OAuthService;
import io.github.eappezo.soundary.services.authentication.application.service.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthAPI {
    private final OAuthService oAuthService;
    private final RefreshService refreshService;

    @Override
    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        LoginResultDto loginResult = oAuthService.login(request.to());

        return LoginResponse.from(loginResult);
    }

    @Override
    @PostMapping("/api/refresh")
    public RefreshResponse refresh(@RequestBody RefreshRequest request) {
        RefreshResultDto refreshResult = refreshService.refresh(request.refreshToken());

        return RefreshResponse.from(refreshResult);
    }
}
