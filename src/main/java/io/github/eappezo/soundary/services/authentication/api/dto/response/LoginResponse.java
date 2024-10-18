package io.github.eappezo.soundary.services.authentication.api.dto.response;

import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.authentication.application.LoginResultDto;

public record LoginResponse(
        UserRole role,
        String accessToken,
        String refreshToken,
        Long expiresIn
) {
    public static LoginResponse from(LoginResultDto loginResult) {
        return new LoginResponse(
                loginResult.role(),
                loginResult.accessToken(),
                loginResult.refreshToken(),
                loginResult.expiresIn()
        );
    }
}