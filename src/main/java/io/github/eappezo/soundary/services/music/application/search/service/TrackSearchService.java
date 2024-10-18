package io.github.eappezo.soundary.services.music.application.search.service;

import io.github.eappezo.soundary.services.music.application.search.SearchedTrackDto;
import io.github.eappezo.soundary.services.music.application.search.TrackBatchInserter;
import io.github.eappezo.soundary.services.music.application.search.TrackSearchGateway;
import io.github.eappezo.soundary.services.music.application.search.TrackSearchGatewayRegistry;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackSearchService {
    private final TrackSearchGatewayRegistry gatewayRegistry;
    private final TrackBatchInserter trackBatchInserter;

    public List<SearchedTrackDto> search(MusicPlatform platform, String query) {
        TrackSearchGateway gateway = gatewayRegistry.getGateway(platform);
        List<SearchedTrackDto> searchResult = gateway.search(query);
        trackBatchInserter.insert(searchResult);
        return searchResult;
    }
}
