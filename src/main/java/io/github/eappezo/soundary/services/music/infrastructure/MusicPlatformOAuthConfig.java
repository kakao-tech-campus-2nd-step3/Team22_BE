package io.github.eappezo.soundary.services.music.infrastructure;

public record MusicPlatformOAuthConfig(
        String tokenUri,
        String clientId,
        String clientSecret
) {
}
