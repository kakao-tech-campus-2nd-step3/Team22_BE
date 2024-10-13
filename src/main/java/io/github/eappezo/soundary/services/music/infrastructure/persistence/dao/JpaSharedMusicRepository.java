package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao;

import io.github.eappezo.soundary.services.music.infrastructure.persistence.SharedMusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSharedMusicRepository extends JpaRepository<SharedMusicEntity, String> {
}
