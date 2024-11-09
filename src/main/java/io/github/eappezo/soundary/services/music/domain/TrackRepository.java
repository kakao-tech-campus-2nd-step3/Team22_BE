package io.github.eappezo.soundary.services.music.domain;

import java.util.Optional;

public interface TrackRepository {

    Optional<Track> findByTrackIdentifier(TrackIdentifier trackIdentifier);

}
