package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import com.querydsl.core.annotations.QueryProjection;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicLikesDto;

public record SharedMusicLikedUserProjection(
        String userId,
        String displayId,
        String nickname,
        String profileImageUrl
) {
    @QueryProjection
    public SharedMusicLikedUserProjection {
    }

    public SharedMusicLikesDto.LikedUser toLikedUser() {
        return new SharedMusicLikesDto.LikedUser(
                Identifier.fromString(userId),
                displayId,
                nickname,
                profileImageUrl
        );
    }
}
