package io.github.eappezo.soundary.core.exception.common;

import io.github.eappezo.soundary.core.exception.APIException;

public class InvalidRequestPayloadException extends APIException {
    public InvalidRequestPayloadException() {
        super(CommonErrorCode.INVALID_REQUEST_PAYLOAD);
    }
}
