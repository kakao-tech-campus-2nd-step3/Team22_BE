package io.github.eappezo.soundary.services.user.api.controller.dto;

public record UserUpdateRequest(
        String nickname,
        String description,
        String profileImageUrl
) {
}
