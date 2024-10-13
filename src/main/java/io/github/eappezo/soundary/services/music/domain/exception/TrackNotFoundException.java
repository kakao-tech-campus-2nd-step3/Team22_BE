package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;

public class TrackNotFoundException extends APIException {
    public TrackNotFoundException() {
        super(MusicErrorCode.TRACK_NOT_FOUND);
    }
}
