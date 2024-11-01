package io.github.eappezo.soundary.services.music.application.share;

import java.util.List;

public record MostLikedTracksDto(
        List<SimpleTrackDto> tracks
) {
    public static MostLikedTracksDto from(List<SimpleTrackDto> tracks) {
        return new MostLikedTracksDto(
                tracks
        );
    }
}
