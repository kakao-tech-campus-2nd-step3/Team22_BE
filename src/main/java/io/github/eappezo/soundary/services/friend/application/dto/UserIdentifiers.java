package io.github.eappezo.soundary.services.friend.application.dto;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.friend.api.dto.FriendRequest;

public record UserIdentifiers(
    Identifier from,
    Identifier to
) {
    public static UserIdentifiers from(FriendRequest friendRequest){
        return new UserIdentifiers(
            Identifier.fromString(friendRequest.fromUserId()),
            Identifier.fromString(friendRequest.toUserId())
        );
    }
}
