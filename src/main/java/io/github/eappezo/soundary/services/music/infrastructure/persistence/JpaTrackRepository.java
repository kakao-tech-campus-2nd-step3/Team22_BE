package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTrackRepository extends JpaRepository<TrackEntity, TrackEntityKey> {
}
