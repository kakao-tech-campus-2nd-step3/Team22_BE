package io.github.eappezo.soundary.services.authentication.application.service;

import io.github.eappezo.soundary.services.authentication.application.*;
import io.github.eappezo.soundary.services.authentication.domain.TokenProvider;
import io.github.eappezo.soundary.services.authentication.domain.exception.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final TokenProvider tokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final RefreshTokenExtendStrategy refreshTokenExtendStrategy;

    public RefreshResultDto refresh(String refreshTokenValue) {
        TokenPayloadDto payload = tokenProvider.extractPayloadFrom(refreshTokenValue);
        RefreshTokenDto refreshToken = validateRefreshToken(refreshTokenValue, payload);

        String accessToken = tokenProvider.generateAccessToken(payload);
        Long expirationTime = tokenProvider.getAccessTokenExpirationTime();

        if (refreshTokenExtendStrategy.hasToExtend(refreshToken)) {
            RefreshTokenDto extendedRefreshToken = RefreshTokenDto.newRefreshToken(
                    tokenProvider.generateRefreshToken(payload)
            );
            userRefreshTokenRepository.save(payload.userId(), extendedRefreshToken);
            return new RefreshResultDto(accessToken, extendedRefreshToken.value(), expirationTime);
        }
        return new RefreshResultDto(accessToken, null, expirationTime);
    }

    private RefreshTokenDto validateRefreshToken(String refreshToken, TokenPayloadDto payload) {
        RefreshTokenDto registeredRefreshToken = userRefreshTokenRepository
                .findRefreshToken(payload.userId())
                .orElseThrow(AuthenticationFailedException::new);
        if (!registeredRefreshToken.equals(refreshToken)) {
            throw new AuthenticationFailedException();
        }
        return registeredRefreshToken;
    }
}
