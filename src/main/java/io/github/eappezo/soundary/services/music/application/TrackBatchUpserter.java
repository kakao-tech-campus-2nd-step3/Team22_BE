package io.github.eappezo.soundary.services.music.application;

import java.util.List;

public interface TrackBatchUpserter {

    void upsert(List<SimpleTrackDto> tracks);

}
