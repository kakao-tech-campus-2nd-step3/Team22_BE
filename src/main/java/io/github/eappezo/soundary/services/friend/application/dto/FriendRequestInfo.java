package io.github.eappezo.soundary.services.friend.application.dto;

import io.github.eappezo.soundary.core.identification.Identifier;

public record FriendRequestInfo(
        Identifier id,
        String displayId,
        String nickname,
        String profileImageUrl
) {
}
