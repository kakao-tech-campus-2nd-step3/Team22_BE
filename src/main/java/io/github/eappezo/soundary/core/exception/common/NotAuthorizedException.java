package io.github.eappezo.soundary.core.exception.common;

import io.github.eappezo.soundary.core.exception.APIException;

public class NotAuthorizedException extends APIException {
    public NotAuthorizedException() {
        super(CommonErrorCode.NOT_AUTHORIZED);
    }
}
