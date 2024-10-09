package io.github.eappezo.soundary.services.music.infrastructure.persistence.repository;

import io.github.eappezo.soundary.services.music.infrastructure.persistence.PlatformTrackLinkEntity;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.PlatformTrackLinkEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPlatformTrackLinkRepository extends JpaRepository<PlatformTrackLinkEntity, PlatformTrackLinkEntityKey> {
}
