package io.github.eappezo.soundary.services.user.application.dto;

import io.github.eappezo.soundary.core.user.User;
import jakarta.annotation.Nullable;

public record UserPatch(
        @Nullable String displayId,
        @Nullable String nickname,
        @Nullable String description,
        @Nullable String profileImageUrl
) {
    public User applyToUser(User user) {
        return new User(
                user.getIdentifier(),
                displayId != null ? displayId : user.getDisplayId(),
                nickname != null ? nickname : user.getNickname(),
                description != null ? description : user.getDescription(),
                profileImageUrl != null ? profileImageUrl : user.getProfileImageUrl(),
                user.getRoles(),
                user.getSignupAt()
        );
    }
}
