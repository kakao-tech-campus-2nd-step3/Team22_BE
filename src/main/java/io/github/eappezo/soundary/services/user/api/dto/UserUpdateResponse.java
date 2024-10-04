package io.github.eappezo.soundary.services.user.api.dto;

import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.application.dto.UserInfo;

import java.util.List;

public record UserUpdateResponse(String displayId,
                                 String nickname,
                                 String description,
                                 String profileImageUrl,
                                 List<UserRole> roles
) {
    public static UserUpdateResponse from(UserInfo userInfo) {
        return new UserUpdateResponse(
                userInfo.displayId(),
                userInfo.nickname(),
                userInfo.description(),
                userInfo.profileImageUrl(),
                userInfo.roles()
        );
    }
}
