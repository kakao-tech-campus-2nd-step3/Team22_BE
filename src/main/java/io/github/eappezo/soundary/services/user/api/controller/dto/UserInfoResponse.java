package io.github.eappezo.soundary.services.user.api.controller.dto;

import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.application.dto.UserInfo;

import java.util.List;

public record UserInfoResponse(
        String displayId,
        String nickname,
        String description,
        String profileImageUrl,
        List<UserRole>roles
) {
    public static UserInfoResponse from(UserInfo userInfo) {
        return new UserInfoResponse(
                userInfo.displayId(),
                userInfo.nickname(),
                userInfo.description(),
                userInfo.profileImageUrl(),
                userInfo.roles());
    }
}
