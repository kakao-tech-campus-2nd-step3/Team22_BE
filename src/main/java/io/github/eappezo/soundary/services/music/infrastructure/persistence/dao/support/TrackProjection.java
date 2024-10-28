package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import com.querydsl.core.annotations.QueryProjection;
import io.github.eappezo.soundary.services.music.application.share.SimpleTrackDto;

public record TrackProjection(
        String trackId,
        String title,
        String serializedArtist,
        String albumTitle,
        String albumCoverUrl,
        String previewMp3Url,
        Long durationInSeconds
) {
    @QueryProjection
    public TrackProjection {
    }

    public SimpleTrackDto toDto() {
        return new SimpleTrackDto(
                trackId,
                title,
                serializedArtist,
                albumTitle,
                albumCoverUrl,
                previewMp3Url,
                durationInSeconds
        );
    }
}
