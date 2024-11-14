package io.github.eappezo.soundary.services.authentication.api.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.authentication.application.LoginResultDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(SnakeCaseStrategy.class)
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