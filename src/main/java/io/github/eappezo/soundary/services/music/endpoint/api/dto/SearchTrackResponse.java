package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.music.domain.Artist;
import io.github.eappezo.soundary.services.music.domain.Track;

import java.util.List;

public record SearchTrackResponse(
        List<TrackResponseDto> tracks
) {
    public static SearchTrackResponse from(List<Track> tracks) {
        return new SearchTrackResponse(tracks.stream().map(TrackResponseDto::from).toList());
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record TrackResponseDto(
            String platform,
            String id,
            String title,
            List<String> artists,
            Long duration,
            String albumCoverUrl,
            String previewMp3Url
    ) {
        public static TrackResponseDto from(Track track) {
            return new TrackResponseDto(
                    track.platform().name(),
                    track.id().toString(),
                    track.title(),
                    track.artists().stream().map(Artist::name).toList(),
                    track.duration().getSeconds(),
                    track.albumCoverUrl(),
                    track.previewMp3Url()
            );
        }
    }
}
