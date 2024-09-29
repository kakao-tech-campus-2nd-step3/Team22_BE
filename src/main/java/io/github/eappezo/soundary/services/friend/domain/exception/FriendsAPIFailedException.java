package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class FriendsAPIFailedException extends APIException {
    public FriendsAPIFailedException(){
        super(FriendsAPIErrorCode.ALREADY_SEND_REQUEST);
    }
}
