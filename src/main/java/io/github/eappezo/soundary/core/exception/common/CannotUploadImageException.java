package io.github.eappezo.soundary.core.exception.common;

import io.github.eappezo.soundary.core.exception.APIException;

public class CannotUploadImageException extends APIException {
    public CannotUploadImageException() {
        super(CommonErrorCode.CANNOT_UPLOAD_IMAGE);
    }
}
