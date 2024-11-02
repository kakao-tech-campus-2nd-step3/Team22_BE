package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.music.application.share.MostLikedTracksDto;
import io.github.eappezo.soundary.services.music.application.share.SimpleTrackDto;

import java.util.Arrays;
import java.util.List;

public record MostLikedTracksResponse(
        List<TrackResponseDto> tracks
) {
    public static MostLikedTracksResponse from(MostLikedTracksDto tracks) {
        return new MostLikedTracksResponse(
                tracks.tracks().stream()
                        .map(TrackResponseDto::from)
                        .toList()
        );
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record TrackResponseDto(
            String trackId,
            String title,
            List<String> artists,
            String albumCoverUrl,
            String previewMp3Url,
            Long durationInSeconds
    ) {
        public static TrackResponseDto from(SimpleTrackDto track) {
            return new TrackResponseDto(
                    track.id(),
                    track.title(),
                    Arrays.stream(
                            track
                                    .serializedArtists()
                                    .split(SimpleTrackDto.ARTISTS_DELIMITER)
                    ).toList(),
                    track.albumCoverUrl(),
                    track.previewMp3Url(),
                    track.durationInSeconds()
            );
        }
    }
}
