package io.github.eappezo.soundary.services.music.application.share;

import java.time.LocalDateTime;

public record SharedMusicByMeDto(
        String id,
        SimpleTrackDto track,
        String comment,
        LocalDateTime sharedAt
) {
}
