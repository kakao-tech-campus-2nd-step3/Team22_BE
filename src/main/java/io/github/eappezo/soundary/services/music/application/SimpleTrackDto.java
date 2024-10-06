package io.github.eappezo.soundary.services.music.application;

import io.github.eappezo.soundary.services.music.domain.Artist;
import io.github.eappezo.soundary.services.music.domain.Track;
import jakarta.annotation.Nullable;

import java.util.List;

public record SimpleTrackDto(
        String id,
        String title,
        String artists,
        String album,
        String albumCoverUrl,
        @Nullable String previewMp3Url,
        Long durationInSeconds
) {
    public static SimpleTrackDto from(Track track) {
        return new SimpleTrackDto(
                track.id().toString(),
                track.title(),
                serialize(track.artists()),
                track.albumTitle(),
                track.albumCoverUrl(),
                track.previewMp3Url(),
                track.duration().getSeconds()
        );
    }

    private static String serialize(List<Artist> artists) {
        return artists.stream().map(Artist::name).reduce((a, b) -> a + ", " + b).orElse("");
    }
}
