package io.github.eappezo.soundary.services.user.application.service;

public record UserUpdateRequest(
        String nickname,
        String description,
        String profileImageUrl
) {
}
