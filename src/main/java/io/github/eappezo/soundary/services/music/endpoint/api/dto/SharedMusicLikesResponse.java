package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicLikesDto;

import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record SharedMusicLikesResponse(
        Integer likes,
        List<LikedUserDto> likedUsers
) {
    public static SharedMusicLikesResponse from(
            SharedMusicLikesDto sharedMusicLikes
    ) {
        return new SharedMusicLikesResponse(
                sharedMusicLikes.likes(),
                sharedMusicLikes.likedUsers().stream()
                        .map(LikedUserDto::from)
                        .toList()
        );
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record LikedUserDto(
            String id,
            String displayId,
            String nickname,
            String profileImageUrl
    ) {
        public static LikedUserDto from(
                SharedMusicLikesDto.LikedUser likedUser
        ) {
            return new LikedUserDto(
                    likedUser.id().toString(),
                    likedUser.displayId(),
                    likedUser.nickname(),
                    likedUser.profileImageUrl()
            );
        }
    }
}
