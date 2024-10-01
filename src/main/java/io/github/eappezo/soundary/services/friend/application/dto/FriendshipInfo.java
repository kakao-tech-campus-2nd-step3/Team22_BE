package io.github.eappezo.soundary.services.friend.application.dto;

import io.github.eappezo.soundary.services.friend.domain.Friend;

public record FriendshipInfo(
    String fromUserId,
    String toUserId
) {
    public static FriendshipInfo from(Friend friend){
        return new FriendshipInfo(
            friend.getFromUserId(),
            friend.getToUserId()
        );
    }
}
