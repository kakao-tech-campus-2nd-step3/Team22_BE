package io.github.eappezo.soundary.services.friend.api.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(SnakeCaseStrategy.class)
public record FriendRequest(
        String targetDisplayId
) {
}
