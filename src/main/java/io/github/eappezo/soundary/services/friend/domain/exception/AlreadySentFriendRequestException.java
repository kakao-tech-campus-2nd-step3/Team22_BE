package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class AlreadySentFriendRequestException extends APIException {
    public AlreadySentFriendRequestException(){
        super(FriendErrorCode.ALREADY_SENT_REQUEST);
    }
}
