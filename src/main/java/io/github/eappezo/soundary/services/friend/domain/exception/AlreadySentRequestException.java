package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class AlreadySentRequestException extends APIException {
    public AlreadySentRequestException(){
        super(FriendErrorCode.ALREADY_SENT_REQUEST);
    }
}
