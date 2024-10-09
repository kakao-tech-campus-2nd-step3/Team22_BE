package io.github.eappezo.soundary.services.music.application;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;

public record PlatformTrackId(
        MusicPlatform platform,
        String platformTrackId
) {
    public static PlatformTrackId of(MusicPlatform platform, String id) {
        return new PlatformTrackId(platform, id);
    }
}
