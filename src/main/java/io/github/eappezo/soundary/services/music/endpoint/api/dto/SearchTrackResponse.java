package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.music.domain.Artist;
import io.github.eappezo.soundary.services.music.domain.Track;

import java.util.List;

public record SearchTrackResponse(
        List<TrackDto> tracks
) {
    public static SearchTrackResponse from(List<Track> tracks) {
        return new SearchTrackResponse(tracks.stream().map(TrackDto::from).toList());
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record TrackDto(
            String id,
            String platform,
            String title,
            List<String> artists,
            Long duration,
            String albumCoverUrl,
            String previewMp3Url
    ) {
        public static TrackDto from(Track track) {
            return new TrackDto(
                    track.id(),
                    track.platform().name(),
                    track.title(),
                    track.artists().stream().map(Artist::name).toList(),
                    track.duration().getSeconds(),
                    track.albumCoverUrl(),
                    track.previewMp3Url()
            );
        }
    }
}
