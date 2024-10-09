package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SharedMusicRepositoryImpl {
    private final JpaSharedMusicRepository jpaSharedMusicRepository;
}
