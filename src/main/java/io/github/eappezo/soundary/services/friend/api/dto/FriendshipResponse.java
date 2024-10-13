package io.github.eappezo.soundary.services.friend.api.dto;

import io.github.eappezo.soundary.services.friend.application.dto.FriendshipInfo;

public record FriendshipResponse(
    String fromUserId,
    String toUserId
) {
    public static FriendshipResponse from(FriendshipInfo friendshipInfo){
        return new FriendshipResponse(friendshipInfo.fromUserId(), friendshipInfo.toUserId());
    }
}
