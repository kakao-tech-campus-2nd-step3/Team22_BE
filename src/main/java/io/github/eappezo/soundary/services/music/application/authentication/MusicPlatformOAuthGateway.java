package io.github.eappezo.soundary.services.music.application.authentication;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.PlatformAccessToken;

public interface MusicPlatformOAuthGateway {

    PlatformAccessToken getAccessToken();

    MusicPlatform getPlatform();

}
