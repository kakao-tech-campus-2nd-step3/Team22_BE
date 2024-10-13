package io.github.eappezo.soundary.services.music.application.search;

import java.util.List;

public interface TrackBatchInserter {

    void insert(List<SearchedTrackDto> tracks);

}
