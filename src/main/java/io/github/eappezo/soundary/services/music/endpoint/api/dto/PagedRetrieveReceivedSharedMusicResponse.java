package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.core.Page;
import io.github.eappezo.soundary.services.music.application.share.ReceivedSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SimpleTrackDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record PagedRetrieveReceivedSharedMusicResponse(
        long total,
        int totalPages,
        List<ReceivedSharedMusicResponseDto> sharedMusics
) {
    public static PagedRetrieveReceivedSharedMusicResponse from(Page<ReceivedSharedMusicDto> sharedMusics) {
        return new PagedRetrieveReceivedSharedMusicResponse(
                sharedMusics.total(),
                sharedMusics.totalPages(),
                sharedMusics.content().stream()
                        .map(ReceivedSharedMusicResponseDto::from)
                        .toList()
        );
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record ReceivedSharedMusicResponseDto(
            String id,
            FromUserResponseDto fromUser,
            ReceivedTrackResponseDto track,
            String comment,
            Boolean isLiked,
            @Schema(example = "2024-11-23 00:00:00", type = "string")
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime sharedAt
    ) {
        public static ReceivedSharedMusicResponseDto from(ReceivedSharedMusicDto sharedMusic) {
            return new ReceivedSharedMusicResponseDto(
                    sharedMusic.id(),
                    FromUserResponseDto.from(sharedMusic.fromUser()),
                    ReceivedTrackResponseDto.from(sharedMusic.track()),
                    sharedMusic.comment(),
                    sharedMusic.isLiked(),
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
        public static FromUserResponseDto from(ReceivedSharedMusicDto.SharedUserInfo fromUser) {
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
    private record ReceivedTrackResponseDto(
            String trackId,
            String title,
            List<String> artists,
            String albumCoverUrl,
            @Schema(nullable = true)
            @Nullable String previewMp3Url,
            Long durationInSeconds
    ) {
        public static ReceivedTrackResponseDto from(SimpleTrackDto track) {
            return new ReceivedTrackResponseDto(
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
