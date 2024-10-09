package io.github.eappezo.soundary.services.music.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.Track;
import io.github.eappezo.soundary.services.music.domain.TrackRepository;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.TrackEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryImpl implements TrackRepository {
    private final JpaTrackRepository jpaTrackRepository;

    @Override
    public Optional<Track> findByIsrc(Identifier id) {
        return jpaTrackRepository
                .findById(id.toString())
                .map(TrackEntity::toDomain);
    }
}
