package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class CannotRequestToMyselfException extends APIException {
    public CannotRequestToMyselfException(){
        super(FriendErrorCode.CANNOT_REQUEST_TO_MYSELF);
    }
}
