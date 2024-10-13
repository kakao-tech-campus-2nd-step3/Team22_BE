package io.github.eappezo.soundary.services.music.application.share.service;

import io.github.eappezo.soundary.core.Page;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import io.github.eappezo.soundary.services.music.application.share.*;
import io.github.eappezo.soundary.services.music.domain.SharedMusicRepository;
import io.github.eappezo.soundary.services.music.domain.exception.AlreadyLikedSharedMusicException;
import io.github.eappezo.soundary.services.music.domain.exception.NotExistsSharedMusicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedMusicService {
    private final SharedMusicRepository sharedMusicRepository;
    private final SharedMusicRetrieveSupport sharedMusicRetrieveSupport;
    private final SharedMusicLikeSupport sharedMusicLikeSupport;
    private final PersistenceOperationGateway persistenceOperationGateway;

    public Page<SentSharedMusicDto> getSentSharedMusic(
            Identifier userId,
            SentSharedMusicQueryCondition condition
    ) {
        return persistenceOperationGateway.executeReadOnlyOperation(
                () -> sharedMusicRetrieveSupport.getSentSharedMusic(userId, condition)
        );
    }

    public List<ReceivedSharedMusicDto> getReceivedSharedMusic(Identifier userId) {
        return persistenceOperationGateway.executeReadOnlyOperation(
                () -> sharedMusicRetrieveSupport.getReceivedSharedMusic(userId)
        );
    }

    @Transactional
    public void likeMusic(Identifier userId, Identifier sharedMusicId) {
        if (sharedMusicRepository.exists(sharedMusicId)) {
            throw new AlreadyLikedSharedMusicException();
        }
        sharedMusicLikeSupport.like(userId, sharedMusicId);
    }

    @Transactional
    public void unlikeMusic(Identifier userId, Identifier sharedMusicId) {
        if (sharedMusicRepository.notExists(sharedMusicId)) {
            throw new NotExistsSharedMusicException();
        }
        sharedMusicLikeSupport.unlike(userId, sharedMusicId);
    }
}
