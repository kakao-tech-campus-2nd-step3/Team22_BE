package io.github.eappezo.soundary.core.exception.common;

import io.github.eappezo.soundary.core.exception.APIException;

public class AlreadyExistsUserException extends APIException {
    public AlreadyExistsUserException() {
        super(CommonErrorCode.ALREADY_EXISTS_USER);
    }
}
