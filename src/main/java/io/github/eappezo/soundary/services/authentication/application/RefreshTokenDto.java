package io.github.eappezo.soundary.services.authentication.application;

import java.time.LocalDateTime;

public record RefreshTokenDto(
        String value,
        LocalDateTime refreshedAt
) {
    public boolean equals(String refreshToken) {
        return value.equals(refreshToken);
    }

    public static RefreshTokenDto newRefreshToken(String value) {
        return new RefreshTokenDto(value, LocalDateTime.now());
    }
}
