package io.github.eappezo.soundary.services.authentication.application;

import io.github.eappezo.soundary.core.user.UserRole;

public record LoginResultDto(
        UserRole role,
        String accessToken,
        String refreshToken,
        Long expiresIn
) {
}
