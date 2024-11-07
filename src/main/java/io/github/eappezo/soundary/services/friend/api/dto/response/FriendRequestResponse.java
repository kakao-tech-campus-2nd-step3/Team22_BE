package io.github.eappezo.soundary.services.friend.api.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;

@JsonNaming(SnakeCaseStrategy.class)
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
