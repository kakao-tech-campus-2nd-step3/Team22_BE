package io.github.eappezo.soundary.services.music.domain;

public interface MusicPlatformAuthenticationManager {

    PlatformAccessToken getAccessToken(MusicPlatform platform);

    void refreshAccessToken(MusicPlatform platform);

}
