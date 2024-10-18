package io.github.eappezo.soundary.services.friend.infrastructure;

import com.querydsl.core.annotations.QueryProjection;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;

public record FriendRequestInfoProjection(
        String id,
        String displayId,
        String nickname,
        String profileImageUrl
) {
    @QueryProjection
    public FriendRequestInfoProjection {
    }

    public FriendRequestInfo toDto() {
        return new FriendRequestInfo(
                Identifier.fromString(id),
                displayId,
                nickname,
                profileImageUrl
        );
    }
}
