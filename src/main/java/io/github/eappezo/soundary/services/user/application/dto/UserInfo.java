package io.github.eappezo.soundary.services.user.application.dto;

import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRole;

import java.util.List;

public record UserInfo(String displayId,
                       String nickname,
                       String description,
                       String profileImageUrl,
                       List<UserRole> roles
) {
    public static UserInfo from(User user) {
        return new UserInfo(
                user.getDisplayId(),
                user.getNickname(),
                user.getDescription(),
                user.getProfileImageUrl(),
                user.getRoles());
    }
}
