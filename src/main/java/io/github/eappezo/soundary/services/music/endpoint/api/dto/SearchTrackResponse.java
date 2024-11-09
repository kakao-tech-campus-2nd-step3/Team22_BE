package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.music.application.search.SearchedTrackDto;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.util.List;

public record SearchTrackResponse(
        List<TrackResponseDto> tracks
) {
    public static SearchTrackResponse from(List<SearchedTrackDto> tracks) {
        return new SearchTrackResponse(tracks.stream().map(TrackResponseDto::from).toList());
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record TrackResponseDto(
            MusicPlatform platform,
            String platformTrackId,
            String title,
            List<String> artists,
            Long duration,
            @Schema(nullable = true)
            @Nullable String albumCoverUrl,
            String previewMp3Url
    ) {
        public static TrackResponseDto from(SearchedTrackDto track) {
            return new TrackResponseDto(
                    track.id().platform(),
                    track.id().platformTrackId(),
                    track.title(),
                    track.artists(),
                    track.durationInSeconds(),
                    track.albumCoverUrl(),
                    track.previewMp3Url()
            );
        }
    }
}
