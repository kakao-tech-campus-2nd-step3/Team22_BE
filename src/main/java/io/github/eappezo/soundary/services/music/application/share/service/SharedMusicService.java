package io.github.eappezo.soundary.services.music.application.share.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.SentSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.ReceivedSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicLikeSupport;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicRetrieveSupport;
import io.github.eappezo.soundary.services.music.domain.SharedMusicRepository;
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

    public List<SentSharedMusicDto> getSentSharedMusic(Identifier userId) {
        return sharedMusicRetrieveSupport.getSentSharedMusic(userId);
    }

    public List<ReceivedSharedMusicDto> getReceivedSharedMusic(Identifier userId) {
        return sharedMusicRetrieveSupport.getReceivedSharedMusic(userId);
    }

    @Transactional
    public void likeMusic(Identifier userId, Identifier sharedMusicId) {
        if (sharedMusicRepository.exists(sharedMusicId)) {
            throw new NotExistsSharedMusicException();
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
