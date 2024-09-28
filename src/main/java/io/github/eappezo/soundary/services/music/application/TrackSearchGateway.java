package io.github.eappezo.soundary.services.music.application;

import io.github.eappezo.soundary.services.music.domain.Track;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;

import java.util.List;

public interface TrackSearchGateway {

    List<Track> search(String query);

    MusicPlatform getPlatform();

}
