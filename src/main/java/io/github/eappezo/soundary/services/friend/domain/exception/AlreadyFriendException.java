package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class AlreadyFriendException extends APIException {
    public AlreadyFriendException(){
        super(FriendErrorCode.ALREADY_FRIEND);
    }
}
