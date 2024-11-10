package io.github.eappezo.soundary.services.friend.infrastructure;

import com.querydsl.core.annotations.QueryProjection;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;

import java.util.Arrays;

public record FriendInfoProjection(
        String id,
        String displayId,
        String nickname,
        String profileImageUrl,
        String serializedLabels
) {
    @QueryProjection
    public FriendInfoProjection {
    }

    public FriendInfo toDto() {
        return new FriendInfo(
                Identifier.fromString(id),
                displayId,
                nickname,
                profileImageUrl,
                Arrays.stream(serializedLabels.split(",")).map(Label::from).toList()
        );
    }
}
