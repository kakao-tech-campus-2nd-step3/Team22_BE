package io.github.eappezo.soundary.services.authentication.application;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.Optional;

public interface UserRefreshTokenRepository {

    void save(Identifier userId, RefreshTokenDto refreshToken);

    Optional<RefreshTokenDto> findRefreshToken(Identifier userId);

}
