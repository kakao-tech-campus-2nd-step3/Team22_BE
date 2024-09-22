package io.github.eappezo.soundary.services.authentication.application.service;

import io.github.eappezo.soundary.services.authentication.application.RefreshResultDto;
import io.github.eappezo.soundary.services.authentication.application.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshService {
    private final TokenProvider tokenProvider;

    public RefreshResultDto refresh(String refreshToken) {
        String accessToken = tokenProvider.generateAccessTokenFromRefreshToken(refreshToken);
        Long expirationTime = tokenProvider.getAccessTokenExpirationTime();

        return new RefreshResultDto(accessToken, expirationTime);
    }
}
