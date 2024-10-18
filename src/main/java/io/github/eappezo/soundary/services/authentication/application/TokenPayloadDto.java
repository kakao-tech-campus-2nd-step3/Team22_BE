package io.github.eappezo.soundary.services.authentication.application;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.UserRole;

import java.util.List;

public record TokenPayloadDto(
        Identifier userId,
        List<UserRole> roles
) {
}
