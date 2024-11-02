package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.SharedMusicRepository;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.JpaSharedMusicTargetRepository;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.SharedMusicTargetEntityKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SharedMusicRepositoryImpl implements SharedMusicRepository {
    private final JpaSharedMusicRepository jpaSharedMusicRepository;
    private final JpaSharedMusicTargetRepository jpaSharedMusicTargetRepository;

    @Override
    public boolean exists(Identifier id) {
        return jpaSharedMusicRepository.existsById(id.toString());
    }

    @Override
    public boolean isSharedBy(Identifier sharedMusicId, Identifier userId) {
        return jpaSharedMusicRepository.existsByIdAndFromUserId(
                sharedMusicId.toString(),
                userId.toString()
        );
    }

    @Override
    public boolean isSharedToUser(Identifier sharedMusicId, Identifier userId) {
        return jpaSharedMusicTargetRepository
                .existsById(SharedMusicTargetEntityKey.of(userId, sharedMusicId));
    }
}
