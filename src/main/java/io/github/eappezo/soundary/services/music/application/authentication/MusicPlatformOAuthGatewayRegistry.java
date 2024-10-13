package io.github.eappezo.soundary.services.music.application.authentication;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;

import java.util.List;

public interface MusicPlatformOAuthGatewayRegistry {

    MusicPlatformOAuthGateway getGateway(MusicPlatform platform);

    List<MusicPlatform> getSupportedPlatforms();

}
