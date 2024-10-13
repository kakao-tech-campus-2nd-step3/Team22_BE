package io.github.eappezo.soundary.services.music.application.share;

import io.github.eappezo.soundary.core.exception.common.InvalidRequestPayloadException;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

public record SentSharedMusicQueryCondition(
        Integer page,
        Integer size,
        @Nullable LocalDateTime startDate,
        @Nullable LocalDateTime endDate
) {
    public SentSharedMusicQueryCondition {
        if (page < 0) {
            throw new InvalidRequestPayloadException();
        }
        if (size < 1) {
            throw new InvalidRequestPayloadException();
        }
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new InvalidRequestPayloadException();
        }
    }

    public long offset() {
        return (long) page * size;
    }
}
