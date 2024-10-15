package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.PlatformTrackId;
import io.github.eappezo.soundary.services.music.domain.Track;
import io.github.eappezo.soundary.services.music.domain.TrackRepository;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.PlatformTrackLinkEntityKey;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.TrackEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryImpl implements TrackRepository {
    private final JpaTrackRepository jpaTrackRepository;
    private final JpaPlatformTrackLinkRepository jpaPlatformTrackLinkRepository;

    @Override
    @Transactional
    public Optional<Track> findByPlatformTrackId(PlatformTrackId platformTrackId) {
        return jpaPlatformTrackLinkRepository
                .findById(PlatformTrackLinkEntityKey.from(platformTrackId))
                .map((trackLink) ->
                        jpaTrackRepository
                                .findById(trackLink.getTrackId())
                                .map(TrackEntity::toDomain)
                                .orElseThrow()
                );
    }

    @Override
    public Optional<Track> findById(Identifier id) {
        return jpaTrackRepository
                .findById(id.toString())
                .map(TrackEntity::toDomain);
    }
}
