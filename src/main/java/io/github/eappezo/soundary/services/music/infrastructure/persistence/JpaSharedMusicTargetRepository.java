package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSharedMusicTargetRepository extends JpaRepository<SharedMusicTargetEntity, SharedMusicTargetEntityKey> {

}
