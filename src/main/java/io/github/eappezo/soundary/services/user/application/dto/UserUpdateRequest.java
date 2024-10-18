package io.github.eappezo.soundary.services.user.application.dto;

public record UserUpdateRequest(
        String nickname,
        String description,
        String profileImageUrl
) {
}
