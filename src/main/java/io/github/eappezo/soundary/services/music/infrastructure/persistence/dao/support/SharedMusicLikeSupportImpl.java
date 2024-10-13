package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicLikeSupport;
import io.github.eappezo.soundary.services.music.domain.exception.AlreadyLikedSharedMusicException;
import io.github.eappezo.soundary.services.music.domain.exception.NotLikedSharedMusicException;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.SharedMusicLikeEntity;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.SharedMusicLikeEntityKey;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.JpaSharedMusicLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SharedMusicLikeSupportImpl implements SharedMusicLikeSupport {
    private final JpaSharedMusicLikeRepository jpaSharedMusicLikeRepository;

    @Override
    @Transactional
    public void like(Identifier userId, Identifier sharedMusicId) {
        SharedMusicLikeEntityKey key = SharedMusicLikeEntityKey.of(sharedMusicId, userId);
        if (jpaSharedMusicLikeRepository.existsById(key)) {
            throw new AlreadyLikedSharedMusicException();
        }
        jpaSharedMusicLikeRepository.save(SharedMusicLikeEntity.from(key));
    }

    @Override
    @Transactional
    public void unlike(Identifier userId, Identifier sharedMusicId) {
        SharedMusicLikeEntityKey key = SharedMusicLikeEntityKey.of(sharedMusicId, userId);
        if (!jpaSharedMusicLikeRepository.existsById(key)) {
            throw new NotLikedSharedMusicException();
        }
        jpaSharedMusicLikeRepository.deleteById(key);
    }
}
