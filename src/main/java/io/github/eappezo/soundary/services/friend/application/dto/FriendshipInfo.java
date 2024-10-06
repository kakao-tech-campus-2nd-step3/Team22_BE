package io.github.eappezo.soundary.services.friend.application.dto;

import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntity;

public record FriendshipInfo(
    String fromUserId,
    String toUserId
) {
    public static FriendshipInfo from(FriendEntity friend){
        return new FriendshipInfo(
            friend.getFromUserId(),
            friend.getToUserId()
        );
    }
}
