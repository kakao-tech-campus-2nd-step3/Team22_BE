package io.github.eappezo.soundary.services.friend.api.dto.response;

import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;

public record FriendRequestResponse(
        String id,
        String displayId,
        String nickname,
        String profileImageUrl
) {
    public static FriendRequestResponse from(FriendRequestInfo friendRequestInfo) {
        return new FriendRequestResponse(
                friendRequestInfo.id().toString(),
                friendRequestInfo.displayId(),
                friendRequestInfo.nickname(),
                friendRequestInfo.profileImageUrl()
        );
    }
}
