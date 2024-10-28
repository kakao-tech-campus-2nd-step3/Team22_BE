package io.github.eappezo.soundary.services.music.application.share;

import java.util.List;

public record MostSharedTracksDto(
        List<SimpleTrackDto> tracks
) {
    public static MostSharedTracksDto from(List<SimpleTrackDto> tracks) {
        return new MostSharedTracksDto(
                tracks
        );
    }
}
