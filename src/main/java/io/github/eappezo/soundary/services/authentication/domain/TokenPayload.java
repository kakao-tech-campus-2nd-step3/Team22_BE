package io.github.eappezo.soundary.services.authentication.domain;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.UserRole;

import java.util.List;

public record TokenPayload(
        Identifier userId,
        List<UserRole> roles
) {
    public TokenPayload include(List<UserRole> roles) {
        return new TokenPayload(userId, roles);
    }
}
