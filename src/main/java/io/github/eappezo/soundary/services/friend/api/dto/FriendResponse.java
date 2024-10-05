package io.github.eappezo.soundary.services.friend.api.dto;

import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;

public record FriendResponse(
    String id,
    String displayId,
    String nickname,
    String description,
    String profileImageUrl
) {
    public static FriendResponse from(FriendInfo friendInfo){
        return new FriendResponse(
            friendInfo.identifier().toString(),
            friendInfo.displayId(),
            friendInfo.nickname(),
            friendInfo.description(),
            friendInfo.profileImageUrl());
    }
}