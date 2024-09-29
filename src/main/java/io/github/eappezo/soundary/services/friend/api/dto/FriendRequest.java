package io.github.eappezo.soundary.services.friend.api.dto;

public record FriendRequest(
    String fromUserId,
    String toUserId
) {
}
