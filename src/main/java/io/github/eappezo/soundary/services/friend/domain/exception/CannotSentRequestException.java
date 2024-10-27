package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class CannotSentRequestException extends APIException {
    public CannotSentRequestException(){
        super(FriendErrorCode.CANNOT_SENT_REQUEST);
    }
}
