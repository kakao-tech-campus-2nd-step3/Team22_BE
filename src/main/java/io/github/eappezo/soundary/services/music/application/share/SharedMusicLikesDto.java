package io.github.eappezo.soundary.services.music.application.share;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.List;

public record SharedMusicLikesDto(
        int likes,
        List<LikedUser> likedUsers
) {
    public record LikedUser(
            Identifier id,
            String displayId,
            String nickname,
            String profileImageUrl
    ) {
    }
}
