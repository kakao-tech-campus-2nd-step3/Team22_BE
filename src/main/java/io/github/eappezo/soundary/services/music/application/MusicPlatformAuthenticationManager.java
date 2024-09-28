package io.github.eappezo.soundary.services.music.application;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;

public interface MusicPlatformAuthenticationManager {

    PlatformAccessToken getAccessToken(MusicPlatform platform);

    void refreshAccessToken(MusicPlatform platform);

}
