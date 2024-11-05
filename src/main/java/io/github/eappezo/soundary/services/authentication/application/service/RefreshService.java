package io.github.eappezo.soundary.services.authentication.application.service;

import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.core.user.UserRoleManager;
import io.github.eappezo.soundary.services.authentication.application.*;
import io.github.eappezo.soundary.services.authentication.domain.TokenPayload;
import io.github.eappezo.soundary.services.authentication.domain.TokenProvider;
import io.github.eappezo.soundary.services.authentication.domain.exception.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final TokenProvider tokenProvider;
    private final UserRoleManager userRoleManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final RefreshTokenExtendStrategy refreshTokenExtendStrategy;

    @Transactional
    public RefreshResultDto refresh(String refreshTokenValue) {
        TokenPayload payload = tokenProvider.extractPayloadFrom(refreshTokenValue);
        RefreshTokenDto refreshToken = validateRefreshToken(refreshTokenValue, payload);

        List<UserRole> userRoles = userRoleManager.getRolesOf(payload.userId());
        String accessToken = tokenProvider.generateAccessToken(payload.include(userRoles));
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

    private RefreshTokenDto validateRefreshToken(String refreshToken, TokenPayload payload) {
        RefreshTokenDto registeredRefreshToken = userRefreshTokenRepository
                .findRefreshToken(payload.userId())
                .orElseThrow(AuthenticationFailedException::new);
        if (!registeredRefreshToken.equals(refreshToken)) {
            throw new AuthenticationFailedException();
        }
        return registeredRefreshToken;
    }
}
