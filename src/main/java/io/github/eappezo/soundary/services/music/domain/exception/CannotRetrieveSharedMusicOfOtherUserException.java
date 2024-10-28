package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class CannotRetrieveSharedMusicOfOtherUserException extends APIException {
    public CannotRetrieveSharedMusicOfOtherUserException() {
        super(MusicErrorCode.CANNOT_RETRIEVE_SHARED_MUSIC_OF_OTHER_USER);
    }
}
