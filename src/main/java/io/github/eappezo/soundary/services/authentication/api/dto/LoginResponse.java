package io.github.eappezo.soundary.services.authentication.api.dto;

import io.github.eappezo.soundary.services.authentication.application.LoginResultDto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        Long expiresIn
) {
    public static LoginResponse from(LoginResultDto userDto) {
        return new LoginResponse(userDto.accessToken(), userDto.refreshToken(), userDto.expiresIn());
    }
}