package io.github.eappezo.soundary.services.user.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.user.application.dto.UserPatch;

@JsonNaming(SnakeCaseStrategy.class)
public record UserUpdateRequest(
        String displayId,
        String nickname,
        String description,
        String profileImageUrl
) {
    public UserPatch toUserPatch() {
        return new UserPatch(
                displayId,
                nickname,
                description,
                profileImageUrl
        );
    }
}
