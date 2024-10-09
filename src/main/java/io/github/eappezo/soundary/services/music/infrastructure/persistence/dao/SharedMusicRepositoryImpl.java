package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.SharedMusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SharedMusicRepositoryImpl implements SharedMusicRepository {
    private final JpaSharedMusicRepository jpaSharedMusicRepository;

    @Override
    public boolean exists(Identifier id) {
        return jpaSharedMusicRepository.existsById(id.toString());
    }
}
