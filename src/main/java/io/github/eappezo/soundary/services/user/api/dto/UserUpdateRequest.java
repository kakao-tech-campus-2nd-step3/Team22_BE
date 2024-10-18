package io.github.eappezo.soundary.services.user.api.dto;

public record UserUpdateRequest(
        String nickname,
        String description,
        String profileImageUrl
) {
}
