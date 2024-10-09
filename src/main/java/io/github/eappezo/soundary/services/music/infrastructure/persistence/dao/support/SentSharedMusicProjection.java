package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import com.querydsl.core.annotations.QueryProjection;
import io.github.eappezo.soundary.services.music.application.share.SentSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SimpleTrackDto;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

public record SentSharedMusicProjection(
        String sharedMusicId,
        String trackId,
        String trackTitle,
        String trackSerializedArtist,
        String albumTitle,
        String albumCoverUrl,
        @Nullable String previewMp3Url,
        Long durationInSeconds,
        String comment,
        LocalDateTime sharedAt
) {
    @QueryProjection
    public SentSharedMusicProjection {
    }

    public SentSharedMusicDto toDto() {
        return new SentSharedMusicDto(
                sharedMusicId,
                getTrack(),
                comment,
                sharedAt
        );
    }

    private SimpleTrackDto getTrack() {
        return new SimpleTrackDto(
                trackId,
                trackTitle,
                trackSerializedArtist,
                albumTitle,
                albumCoverUrl,
                previewMp3Url,
                durationInSeconds
        );
    }
}
