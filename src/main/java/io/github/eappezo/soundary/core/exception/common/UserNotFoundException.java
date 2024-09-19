package io.github.eappezo.soundary.core.exception.common;

import io.github.eappezo.soundary.core.exception.APIException;

public class UserNotFoundException extends APIException {
    public UserNotFoundException() {
        super(CommonErrorCode.USER_NOT_FOUND);
    }
}
