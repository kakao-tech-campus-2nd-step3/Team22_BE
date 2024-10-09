package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import io.github.eappezo.soundary.services.music.domain.SharedMusic;

public record ShareMusicResponse(
        String sharedMusicId
) {
    public static ShareMusicResponse from(SharedMusic sharedMusic) {
        return new ShareMusicResponse(
                sharedMusic.id().toString()
        );
    }
}
