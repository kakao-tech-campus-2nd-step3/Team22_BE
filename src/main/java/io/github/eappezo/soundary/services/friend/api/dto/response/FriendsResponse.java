package io.github.eappezo.soundary.services.friend.api.dto.response;

import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import java.util.List;

public record FriendsResponse(
    List<FriendResponse> friends
) {
    public static FriendsResponse from(List<FriendInfo> friendInfoList){
        List<FriendResponse> friendResponseList = friendInfoList.stream()
            .map(FriendResponse::from)
            .toList();

        return new FriendsResponse(friendResponseList);
    }

    public record FriendResponse(
            String id,
            String displayId,
            String nickname,
            String profileImageUrl
    ) {
        public static FriendResponse from(FriendInfo friendInfo) {
            return new FriendResponse(
                    friendInfo.id().toString(),
                    friendInfo.displayId(),
                    friendInfo.nickname(),
                    friendInfo.profileImageUrl()
            );
        }
    }
}
