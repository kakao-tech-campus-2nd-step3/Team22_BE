package io.github.eappezo.soundary.services.music.application;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MusicPlatformOAuthGatewayRegistryImpl implements MusicPlatformOAuthGatewayRegistry {
    private final Map<MusicPlatform, MusicPlatformOAuthGateway> gateways;

    @Autowired
    public MusicPlatformOAuthGatewayRegistryImpl(List<MusicPlatformOAuthGateway> gateways) {
        this.gateways = gateways.stream()
                .collect(
                        Collectors.toMap(MusicPlatformOAuthGateway::getPlatform, Function.identity())
                );
    }

    @Override
    public MusicPlatformOAuthGateway getGateway(MusicPlatform platform) {
        return gateways.get(platform);
    }

    @Override
    public List<MusicPlatform> getSupportedPlatforms() {
        return new LinkedList<>(gateways.keySet());
    }
}
