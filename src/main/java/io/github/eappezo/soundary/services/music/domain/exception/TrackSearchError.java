package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.APIException;
import lombok.Getter;

@Getter
public class TrackSearchError extends APIException {
    private final String query;

    public TrackSearchError(String query) {
        super(MusicErrorCode.TRACK_SEARCH_ERROR, "Failed to search track with query: " + query);
        this.query = query;
    }
}
