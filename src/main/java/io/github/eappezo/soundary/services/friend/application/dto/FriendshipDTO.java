package io.github.eappezo.soundary.services.friend.application.dto;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.friend.api.dto.FriendRequest;

public record FriendshipDTO(
    Identifier from,
    Identifier to
) {
    public static FriendshipDTO from(FriendRequest friendRequest){
        return new FriendshipDTO(
            Identifier.fromString(friendRequest.fromUserId()),
            Identifier.fromString(friendRequest.toUserId())
        );
    }
}
