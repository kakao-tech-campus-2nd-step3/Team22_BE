package io.github.eappezo.soundary.services.music.application.authentication;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.MusicPlatformAuthenticationManager;
import io.github.eappezo.soundary.services.music.domain.PlatformAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MusicPlatformAuthenticationManagerImpl implements MusicPlatformAuthenticationManager {
    private final MusicPlatformOAuthGatewayRegistry gatewayRegistry;
    private final MusicPlatformAuthenticationCacheAdvice cacheAdvice;

    @Override
    public PlatformAccessToken getAccessToken(MusicPlatform platform) {
        return cacheAdvice.lookAside(() -> {
            return gatewayRegistry.getGateway(platform).getAccessToken();
        }, platform);
    }

    @Override
    public void refreshAccessToken(MusicPlatform platform) {
        cacheAdvice.update(() -> {
            return gatewayRegistry.getGateway(platform).getAccessToken();
        }, platform);
    }
}
