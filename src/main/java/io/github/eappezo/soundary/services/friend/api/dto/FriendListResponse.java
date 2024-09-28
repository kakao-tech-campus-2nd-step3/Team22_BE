package io.github.eappezo.soundary.services.friend.api.dto;

import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import java.util.List;

public record FriendListResponse(
    List<FriendResponse> friends
) {
    public static FriendListResponse from(List<FriendInfo> friendInfoList){
        List<FriendResponse> friendResponseList = friendInfoList.stream()
            .map(FriendResponse::from)
            .toList();

        return new FriendListResponse(friendResponseList);
    }
}
