package io.github.eappezo.soundary.services.music.application.search;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;

import java.util.List;

public interface TrackSearchGateway {

    List<SearchedTrackDto> search(String query);

    MusicPlatform getPlatform();

}
