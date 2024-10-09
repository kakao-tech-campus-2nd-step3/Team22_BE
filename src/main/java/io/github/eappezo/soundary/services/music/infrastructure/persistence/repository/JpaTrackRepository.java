package io.github.eappezo.soundary.services.music.infrastructure.persistence.repository;

import io.github.eappezo.soundary.services.music.infrastructure.persistence.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTrackRepository extends JpaRepository<TrackEntity, String> {
}
