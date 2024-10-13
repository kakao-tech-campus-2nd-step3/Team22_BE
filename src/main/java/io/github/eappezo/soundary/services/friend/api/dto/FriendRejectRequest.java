package io.github.eappezo.soundary.services.friend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.eappezo.soundary.core.identification.Identifier;

public record FriendRejectRequest(
    @JsonProperty("to_user_id")
    String rawToUserId
) {
    public Identifier toUserId() {
        return Identifier.fromString(rawToUserId);
    }
}
