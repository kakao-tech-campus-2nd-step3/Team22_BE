package io.github.eappezo.soundary.services.authentication.application;

public record RefreshResultDto(
        String accessToken,
        Long expiresIn
) {
}
