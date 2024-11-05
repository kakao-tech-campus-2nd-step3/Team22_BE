package io.github.eappezo.soundary.services.user.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class AlreadyInitializedUserException extends APIException {
    public AlreadyInitializedUserException() {
        super(UserErrorCode.ALREADY_INITIALIZED_USER);
    }
}
