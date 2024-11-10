package io.github.eappezo.soundary.services.friend.application.dto;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;

import java.util.List;

public record FriendInfo(
    Identifier id,
    String displayId,
    String nickname,
    String profileImageUrl,
    List<Label> labels
) {
}
