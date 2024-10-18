package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class AlreadyLikedSharedMusicException extends APIException {
    public AlreadyLikedSharedMusicException() {
        super(MusicErrorCode.ALREADY_LIKED_SHARED_MUSIC);
    }
}
