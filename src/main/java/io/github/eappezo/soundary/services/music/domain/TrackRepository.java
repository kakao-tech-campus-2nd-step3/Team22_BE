package io.github.eappezo.soundary.services.music.domain;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.Optional;

public interface TrackRepository {

    Optional<Track> findByPlatformTrackId(PlatformTrackId platformTrackId);

    Optional<Track> findById(Identifier id);

}
