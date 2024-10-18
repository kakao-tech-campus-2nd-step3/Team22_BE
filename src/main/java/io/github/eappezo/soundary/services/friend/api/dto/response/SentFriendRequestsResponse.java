package io.github.eappezo.soundary.services.friend.api.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;

import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record SentFriendRequestsResponse(
        List<FriendRequestResponse> sentRequests
) {
    public static SentFriendRequestsResponse from(List<FriendRequestInfo> friendRequests) {
        List<FriendRequestResponse> friendRequestResponseList = friendRequests.stream()
                .map(FriendRequestResponse::from)
                .toList();

        return new SentFriendRequestsResponse(friendRequestResponseList);
    }
}
