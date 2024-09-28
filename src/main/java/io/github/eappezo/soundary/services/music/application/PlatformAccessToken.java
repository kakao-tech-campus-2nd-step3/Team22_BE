package io.github.eappezo.soundary.services.music.application;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;

public record PlatformAccessToken(
        MusicPlatform platform,
        String token,
        Long expiresIn
) {
    public static PlatformAccessToken of(MusicPlatform platform, String token, Long expiresIn) {
        return new PlatformAccessToken(platform, token, expiresIn);
    }
}
