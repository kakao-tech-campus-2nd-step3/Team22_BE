package io.github.eappezo.soundary.services.authentication.application;

import jakarta.annotation.Nullable;

public record RefreshResultDto(
        String accessToken,
        @Nullable String refreshToken,
        Long expiresIn
) {
}
