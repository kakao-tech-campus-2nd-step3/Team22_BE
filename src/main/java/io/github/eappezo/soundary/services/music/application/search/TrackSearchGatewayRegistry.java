package io.github.eappezo.soundary.services.music.application.search;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;

public interface TrackSearchGatewayRegistry {

    TrackSearchGateway getGateway(MusicPlatform platform);

}
