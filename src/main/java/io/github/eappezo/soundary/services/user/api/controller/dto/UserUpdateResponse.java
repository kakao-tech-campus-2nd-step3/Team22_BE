package io.github.eappezo.soundary.services.user.api.controller.dto;

import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRole;

import java.util.List;

public record UserUpdateResponse(String displayId,
                                 String nickname,
                                 String description,
                                 String profileImageUrl,
                                 List<UserRole> roles
) {
    public static UserUpdateResponse from(User user) {
        return new UserUpdateResponse(
                user.getDisplayId(),
                user.getNickname(),
                user.getDescription(),
                user.getProfileImageUrl(),
                user.getRoles()
        );
    }
}
