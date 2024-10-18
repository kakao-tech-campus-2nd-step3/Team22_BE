package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class NotSupportedPlatform extends APIException {
    public NotSupportedPlatform() {
        super(MusicErrorCode.NOT_SUPPORTED_PLATFORM);
    }
}
