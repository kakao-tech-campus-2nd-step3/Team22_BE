package io.github.eappezo.soundary.services.music.application;

import java.util.List;

public interface TrackBatchProducer {

    void produce(List<SimpleTrackDto> tracks);

}
