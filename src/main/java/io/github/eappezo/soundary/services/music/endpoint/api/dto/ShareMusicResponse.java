package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.music.domain.SharedMusic;

@JsonNaming(SnakeCaseStrategy.class)
public record ShareMusicResponse(
        String sharedMusicId
) {
    public static ShareMusicResponse from(SharedMusic sharedMusic) {
        return new ShareMusicResponse(
                sharedMusic.id().toString()
        );
    }
}
