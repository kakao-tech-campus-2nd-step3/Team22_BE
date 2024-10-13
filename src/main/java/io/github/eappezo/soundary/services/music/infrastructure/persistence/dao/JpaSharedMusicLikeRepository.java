package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao;

import io.github.eappezo.soundary.services.music.infrastructure.persistence.SharedMusicLikeEntity;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.SharedMusicLikeEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSharedMusicLikeRepository extends JpaRepository<SharedMusicLikeEntity, SharedMusicLikeEntityKey> {

}
