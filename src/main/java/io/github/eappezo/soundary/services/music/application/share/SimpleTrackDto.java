package io.github.eappezo.soundary.services.music.application.share;

public record SimpleTrackDto(
        String id,
        String title,
        String serializedArtists,
        String albumTitle,
        String albumCoverUrl,
        String previewMp3Url,
        Long durationInSeconds
) {
}