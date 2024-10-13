package io.github.eappezo.soundary.services.user.application.dto;

public record UserUpdate(
        String nickname,
        String description,
        String profileImageUrl
) {
}
