package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class FriendLimitException extends APIException {
    public FriendLimitException(){
        super(FriendErrorCode.FRIEND_LIMIT);
    }
}
