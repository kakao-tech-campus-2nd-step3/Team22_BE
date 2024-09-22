package io.github.eappezo.soundary.services.authentication.api.dto;

import io.github.eappezo.soundary.services.authentication.application.RefreshResultDto;

public record RefreshResponse(
        String accessToken,
        Long expiresIn
) {
    public static RefreshResponse from(RefreshResultDto userDto) {
        return new RefreshResponse(userDto.accessToken(), userDto.expiresIn());
    }
}
