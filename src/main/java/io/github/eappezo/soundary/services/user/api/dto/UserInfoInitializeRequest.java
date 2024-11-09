package io.github.eappezo.soundary.services.user.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.user.application.dto.UserPatch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record UserInfoInitializeRequest(
        List<Label> labels,
        String deviceToken,
        String displayId,
        String nickname,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Nullable String description,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Nullable String profileImageUrl
) {
    public UserPatch extractUserPatch() {
        return new UserPatch(displayId, nickname, description, profileImageUrl);
    }
}
