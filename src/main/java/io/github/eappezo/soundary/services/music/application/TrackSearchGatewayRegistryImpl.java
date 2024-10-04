package io.github.eappezo.soundary.services.music.application;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrackSearchGatewayRegistryImpl implements TrackSearchGatewayRegistry {
    private final List<TrackSearchGateway> gateways;

    @Override
    public TrackSearchGateway getGateway(MusicPlatform platform) {
        return gateways.stream()
                .filter(gateway -> gateway.getPlatform() == platform)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No gateway found for platform " + platform));
    }
}
