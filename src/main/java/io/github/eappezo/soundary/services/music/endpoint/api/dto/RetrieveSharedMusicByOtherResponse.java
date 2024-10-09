package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicByOtherDto;
import io.github.eappezo.soundary.services.music.application.share.SimpleTrackDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record RetrieveSharedMusicByOtherResponse(
        List<SharedMusicResponseDto> sharedMusics
) {
    public static RetrieveSharedMusicByOtherResponse from(List<SharedMusicByOtherDto> sharedMusics) {
        return new RetrieveSharedMusicByOtherResponse(
                sharedMusics.stream()
                        .map(SharedMusicResponseDto::from)
                        .toList()
        );
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record SharedMusicResponseDto(
            String id,
            FromUserResponseDto fromUser,
            TrackResponseDto track,
            String comment,
            @Schema(example = "2024-11-23 00:00:00", type = "string")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime sharedAt
    ) {
        public static SharedMusicResponseDto from(SharedMusicByOtherDto sharedMusic) {
            return new SharedMusicResponseDto(
                    sharedMusic.id(),
                    FromUserResponseDto.from(sharedMusic.fromUser()),
                    TrackResponseDto.from(sharedMusic.track()),
                    sharedMusic.comment(),
                    sharedMusic.sharedAt()
            );
        }
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record FromUserResponseDto(
            String id,
            String displayName,
            String name,
            String profileImageUrl
    ) {
        public static FromUserResponseDto from(SharedMusicByOtherDto.SharedUserInfo fromUser) {
            return new FromUserResponseDto(
                    fromUser.id(),
                    fromUser.displayId(),
                    fromUser.nickname(),
                    fromUser.profileImageUrl()
            );
        }
    }

    @JsonNaming(SnakeCaseStrategy.class)
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
                    Arrays.stream(track.serializedArtists().split(", ")).toList(),
                    track.albumCoverUrl(),
                    track.previewMp3Url(),
                    track.durationInSeconds()
            );
        }
    }
}
