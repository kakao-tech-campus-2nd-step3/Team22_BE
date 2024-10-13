package io.github.eappezo.soundary.services.music.application.authentication.service;

import io.github.eappezo.soundary.services.music.application.authentication.MusicPlatformOAuthGatewayRegistry;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.MusicPlatformAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MusicPlatformAuthenticationRefreshService {
    private final MusicPlatformOAuthGatewayRegistry gatewayRegistry;
    private final MusicPlatformAuthenticationManager authenticationManager;

    public void refreshAll() {
        for (MusicPlatform platform : gatewayRegistry.getSupportedPlatforms()) {
            authenticationManager.refreshAccessToken(platform);
        }
    }
}
