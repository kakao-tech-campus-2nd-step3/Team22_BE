package io.github.eappezo.soundary.services.user.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.user.application.dto.UserPatch;

import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public record UserInfoInitializeRequest(
        List<Label> labels,
        String deviceToken,
        String displayId,
        String nickname,
        String description,
        String profileImageUrl
) {
    public UserPatch extractUserPatch() {
        return new UserPatch(displayId, nickname, description, profileImageUrl);
    }
}
