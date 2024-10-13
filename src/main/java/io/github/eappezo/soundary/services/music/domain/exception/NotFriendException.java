package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class NotFriendException extends APIException {
    public NotFriendException() {
        super(MusicErrorCode.NOT_FRIEND);
    }
}
