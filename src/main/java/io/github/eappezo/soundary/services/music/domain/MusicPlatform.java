package io.github.eappezo.soundary.services.music.domain;

import io.github.eappezo.soundary.services.music.domain.exception.NotSupportedPlatform;

public enum MusicPlatform {
    SPOTIFY;

    public static MusicPlatform fromString(String platform) {
        try {
            return MusicPlatform.valueOf(platform.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotSupportedPlatform();
        }
    }
}
