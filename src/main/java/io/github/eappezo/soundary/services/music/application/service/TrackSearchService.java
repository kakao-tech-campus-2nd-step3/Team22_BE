package io.github.eappezo.soundary.services.music.application.service;

import io.github.eappezo.soundary.services.music.application.TrackSearchGateway;
import io.github.eappezo.soundary.services.music.application.TrackSearchGatewayRegistry;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackSearchService {
    private final TrackSearchGatewayRegistry gatewayRegistry;

    // TODO 검색 시 나온 track 중 track repository 에 없는 트랙은 저장해야 함 (kafka producer 도입)
    public List<Track> search(MusicPlatform platform, String query) {
        TrackSearchGateway gateway = gatewayRegistry.getGateway(platform);

        return gateway.search(query);
    }
}
