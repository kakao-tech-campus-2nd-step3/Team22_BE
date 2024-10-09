package io.github.eappezo.soundary.services.music.application.authentication;

import io.github.eappezo.soundary.services.music.domain.PlatformAccessToken;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;

public interface MusicPlatformOAuthGateway {

    PlatformAccessToken getAccessToken();

    MusicPlatform getPlatform();

}
