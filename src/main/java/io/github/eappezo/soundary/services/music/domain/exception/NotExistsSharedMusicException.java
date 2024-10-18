package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class NotExistsSharedMusicException extends APIException {
    public NotExistsSharedMusicException() {
        super(MusicErrorCode.NOT_EXISTS_SHARED_MUSIC);
    }
}
