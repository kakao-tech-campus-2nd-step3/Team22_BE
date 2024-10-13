package io.github.eappezo.soundary.services.music.domain;

public record PlatformAccessToken(
        MusicPlatform platform,
        String value,
        Long expiresIn
) {
    public static PlatformAccessToken of(MusicPlatform platform, String token, Long expiresIn) {
        return new PlatformAccessToken(platform, token, expiresIn);
    }
}
