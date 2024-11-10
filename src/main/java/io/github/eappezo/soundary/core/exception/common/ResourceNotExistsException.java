package io.github.eappezo.soundary.core.exception.common;

import io.github.eappezo.soundary.core.exception.APIException;

public class ResourceNotExistsException extends APIException {
    public ResourceNotExistsException() {
        super(CommonErrorCode.RESOURCE_NOT_EXISTS);
    }
}
