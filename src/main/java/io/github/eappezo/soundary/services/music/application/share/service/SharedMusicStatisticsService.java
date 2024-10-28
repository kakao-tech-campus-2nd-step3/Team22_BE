package io.github.eappezo.soundary.services.music.application.share.service;

import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import io.github.eappezo.soundary.services.music.application.share.MostLikedTracksDto;
import io.github.eappezo.soundary.services.music.application.share.MostSharedTracksDto;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicStatisticsCacheAdvice;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicStatisticsSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharedMusicStatisticsService {
    private final static String MOST_SHARED_TRACKS = "most-shared-tracks";
    private final static String MOST_LIKED_TRACKS = "most-liked-tracks";

    private final SharedMusicStatisticsCacheAdvice cacheAdvice;
    private final SharedMusicStatisticsSupport sharedMusicStatisticsSupport;
    private final PersistenceOperationGateway persistenceOperationGateway;

    public MostSharedTracksDto getMostSharedTracks() {
        return cacheAdvice.lookAside(
                () -> persistenceOperationGateway.executeReadOnlyOperation(
                        sharedMusicStatisticsSupport::getMostSharedTracks
                ),
                MOST_SHARED_TRACKS
        );
    }

    public MostLikedTracksDto getMostLikedTracks() {
        return cacheAdvice.lookAside(
                () -> persistenceOperationGateway.executeReadOnlyOperation(
                        sharedMusicStatisticsSupport::getMostLikedTracks
                ),
                MOST_LIKED_TRACKS
        );
    }

    public void refreshStatistics() {
        cacheAdvice.evict(MOST_SHARED_TRACKS);
        cacheAdvice.evict(MOST_LIKED_TRACKS);

        getMostLikedTracks();
        getMostSharedTracks();
    }
}
