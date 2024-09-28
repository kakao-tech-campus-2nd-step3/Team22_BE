package io.github.eappezo.soundary.services.music.application;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;

public interface MusicPlatformOAuthGateway {

    PlatformAccessToken getAccessToken();

    MusicPlatform getPlatform();

}
