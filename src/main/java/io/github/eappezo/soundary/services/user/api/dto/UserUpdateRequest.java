package io.github.eappezo.soundary.services.user.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.user.application.dto.UserPatch;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(SnakeCaseStrategy.class)
public record UserUpdateRequest(
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String displayId,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String nickname,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String description,

        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
