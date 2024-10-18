package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class NotLikedSharedMusicException extends APIException {
    public NotLikedSharedMusicException() {
        super(MusicErrorCode.NOT_LIKED_SHARED_MUSIC);
    }
}
