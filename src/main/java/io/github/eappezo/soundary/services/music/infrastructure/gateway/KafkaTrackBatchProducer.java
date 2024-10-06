package io.github.eappezo.soundary.services.music.infrastructure.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eappezo.soundary.services.music.application.SimpleTrackDto;
import io.github.eappezo.soundary.services.music.application.TrackBatchProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaTrackBatchProducer implements TrackBatchProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic = "tracks";

    @Override
    public void produce(List<SimpleTrackDto> tracks) {
        try {
            kafkaTemplate.send(topic, objectMapper.writeValueAsString(tracks));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
