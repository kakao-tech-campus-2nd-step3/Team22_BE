package io.github.eappezo.soundary.services.music.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SharedMusicRepositoryImpl {
    private final JpaSharedMusicRepository jpaSharedMusicRepository;
}
