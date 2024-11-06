package io.github.eappezo.soundary.services.user.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.application.dto.UserInfo;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(SnakeCaseStrategy.class)
public record UserInfoResponse(
        String displayId,
        String nickname,
        String description,
        String profileImageUrl,
        List<UserRole> roles,
        List<Label> labels
) {
    public static UserInfoResponse from(UserInfo userInfo) {
        return new UserInfoResponse(
                userInfo.displayId(),
                userInfo.nickname(),
                userInfo.description(),
                userInfo.profileImageUrl(),
                userInfo.roles(),
                userInfo.labels()
        );
    }
}
