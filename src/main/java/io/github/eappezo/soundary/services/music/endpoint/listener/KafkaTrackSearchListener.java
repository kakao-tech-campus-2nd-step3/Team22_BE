package io.github.eappezo.soundary.services.music.endpoint.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eappezo.soundary.services.music.application.SimpleTrackDto;
import io.github.eappezo.soundary.services.music.application.TrackBatchUpserter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaTrackSearchListener {
    private final ObjectMapper objectMapper;
    private final TrackBatchUpserter trackBatchUpserter;

    @KafkaListener(topics = "tracks", groupId = "track-consumer-group")
    public void consumeTracks(String tracksJson) throws Exception {
        SimpleTrackDto[] tracks = objectMapper.readValue(tracksJson, SimpleTrackDto[].class);
        trackBatchUpserter.upsert(Arrays.stream(tracks).toList());
    }
}
