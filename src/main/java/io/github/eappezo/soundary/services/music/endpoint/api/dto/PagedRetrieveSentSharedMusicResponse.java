package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.core.Page;
import io.github.eappezo.soundary.services.music.application.share.SentSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SimpleTrackDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record PagedRetrieveSentSharedMusicResponse(
        long total,
        int totalPages,
        List<SentSharedMusicResponseDto> sharedMusics
) {
    public static PagedRetrieveSentSharedMusicResponse from(
            Page<SentSharedMusicDto> sharedMusics
    ) {
        return new PagedRetrieveSentSharedMusicResponse(
                sharedMusics.total(),
                sharedMusics.totalPages(),
                sharedMusics.content()
                        .stream()
                        .map(SentSharedMusicResponseDto::from)
                        .toList()
        );
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record SentSharedMusicResponseDto(
            String id,
            SentTrackResponseDto track,
            String comment,
            @Schema(example = "2024-11-23 00:00:00", type = "string")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime sharedAt
    ) {
        public static SentSharedMusicResponseDto from(SentSharedMusicDto sharedMusic) {
            return new SentSharedMusicResponseDto(
                    sharedMusic.id(),
                    SentTrackResponseDto.from(sharedMusic.track()),
                    sharedMusic.comment(),
                    sharedMusic.sharedAt()
            );
        }
    }

    @JsonNaming(SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private record SentTrackResponseDto(
            String trackId,
            String title,
            List<String> artists,
            String albumCoverUrl,
            @Schema(nullable = true)
            @Nullable String previewMp3Url,
            Long durationInSeconds
    ) {
        public static SentTrackResponseDto from(SimpleTrackDto track) {
            return new SentTrackResponseDto(
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
