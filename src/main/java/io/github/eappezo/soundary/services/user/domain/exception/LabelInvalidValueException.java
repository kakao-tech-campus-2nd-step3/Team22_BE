package io.github.eappezo.soundary.services.user.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class LabelInvalidValueException extends APIException {

    public LabelInvalidValueException() {
        super(LabelErrorCode.LABEL_INVALID_VALUE);
    }
}
