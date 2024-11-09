package io.github.eappezo.soundary.services.authentication.domain;

import io.github.eappezo.soundary.core.user.User;

public interface TokenProvider {

    String generateAccessTokenFrom(User user);

    String generateRefreshTokenFrom(User user);

    String generateAccessToken(TokenPayload payload);

    String generateRefreshToken(TokenPayload payload);

    TokenPayload extractPayloadFrom(String token);

    Long getAccessTokenExpirationTime();

}
