package io.github.eappezo.soundary.services.music.application.search;

import io.github.eappezo.soundary.services.music.domain.PlatformTrackId;
import jakarta.annotation.Nullable;

import java.util.List;

public record SearchedTrackDto(
        PlatformTrackId id,
        String title,
        List<String> artists,
        String album,
        String albumCoverUrl,
        @Nullable String previewMp3Url,
        Long durationInSeconds
) {
}
