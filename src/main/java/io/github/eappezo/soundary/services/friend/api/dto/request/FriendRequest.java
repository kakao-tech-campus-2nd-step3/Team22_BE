package io.github.eappezo.soundary.services.friend.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.eappezo.soundary.core.identification.Identifier;

public record FriendRequest(
    @JsonProperty("to_user_id")
    String rawToUserId
) {
    public Identifier toUserId() {
        return Identifier.fromString(rawToUserId);
    }
}
