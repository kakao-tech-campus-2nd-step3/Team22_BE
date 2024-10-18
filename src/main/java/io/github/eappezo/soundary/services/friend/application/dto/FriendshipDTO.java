package io.github.eappezo.soundary.services.friend.application.dto;

import io.github.eappezo.soundary.core.identification.Identifier;

public record FriendshipDTO(
    Identifier from,
    Identifier to
) {
    public static FriendshipDTO of(Identifier userId, Identifier targetUserId){
        return new FriendshipDTO(
            userId,
            targetUserId
        );
    }

    public FriendshipDTO reverse() {
        return new FriendshipDTO(to, from);
    }
}
