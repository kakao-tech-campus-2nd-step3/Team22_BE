package io.github.eappezo.soundary.services.authentication.domain;

import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.services.authentication.application.TokenPayloadDto;

public interface TokenProvider {

    String generateAccessTokenFrom(User user);

    String generateRefreshTokenFrom(User user);

    String generateAccessToken(TokenPayloadDto payload);

    String generateRefreshToken(TokenPayloadDto payload);

    TokenPayloadDto extractPayloadFrom(String token);

    Long getAccessTokenExpirationTime();

}
