package io.github.eappezo.soundary.services.user.api.dto;

import io.github.eappezo.soundary.services.user.application.dto.UserPatch;

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
