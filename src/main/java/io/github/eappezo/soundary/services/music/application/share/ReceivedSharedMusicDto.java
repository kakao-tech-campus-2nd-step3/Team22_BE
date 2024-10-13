package io.github.eappezo.soundary.services.music.application.share;

import java.time.LocalDateTime;

public record ReceivedSharedMusicDto(
        String id,
        SharedUserInfo fromUser,
        SimpleTrackDto track,
        String comment,
        LocalDateTime sharedAt
) {
    public record SharedUserInfo(
            String id,
            String displayId,
            String nickname,
            String profileImageUrl
    ) {
    }
}
