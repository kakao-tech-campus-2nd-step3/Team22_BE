package io.github.eappezo.soundary.services.authentication.application;

public record LoginResultDto(
        String accessToken,
        String refreshToken,
        Long expiresIn
) {
}
