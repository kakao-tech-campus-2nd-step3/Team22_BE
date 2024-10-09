package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import com.querydsl.core.annotations.QueryProjection;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicByOtherDto;
import io.github.eappezo.soundary.services.music.application.share.SimpleTrackDto;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;

public record SharedMusicByOtherProjection(
        String sharedMusicId,
        String fromUserId,
        String fromUserDisplayId,
        String fromUserNickname,
        String fromUserProfileImageUrl,
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
    public SharedMusicByOtherProjection {
    }

    public SharedMusicByOtherDto toDto() {
        return new SharedMusicByOtherDto(
                sharedMusicId,
                getSharedUserInfo(),
                getTrack(),
                comment,
                sharedAt
        );
    }

    private SharedMusicByOtherDto.SharedUserInfo getSharedUserInfo() {
        return new SharedMusicByOtherDto.SharedUserInfo(
                fromUserId,
                fromUserDisplayId,
                fromUserNickname,
                fromUserProfileImageUrl
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
