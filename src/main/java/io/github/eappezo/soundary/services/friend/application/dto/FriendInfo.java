package io.github.eappezo.soundary.services.friend.application.dto;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;

public record FriendInfo(
    Identifier id,
    String displayId,
    String nickname,
    String profileImageUrl
) {
    public static FriendInfo from(User user){
        return new FriendInfo(
            user.getIdentifier(),
            user.getDisplayId(),
            user.getNickname(),
            user.getProfileImageUrl());
    }
}
