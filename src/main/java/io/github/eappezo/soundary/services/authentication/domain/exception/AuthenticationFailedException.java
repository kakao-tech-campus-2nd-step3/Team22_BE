package io.github.eappezo.soundary.services.authentication.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class AuthenticationFailedException extends APIException {
    public AuthenticationFailedException() {
        super(AuthenticationErrorCode.AUTHENTICATION_FAILED);
    }
}
