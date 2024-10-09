package io.github.eappezo.soundary.services.music.domain;

import io.github.eappezo.soundary.services.music.application.PlatformAccessToken;

public interface MusicPlatformAuthenticationManager {

    PlatformAccessToken getAccessToken(MusicPlatform platform);

    void refreshAccessToken(MusicPlatform platform);

}
