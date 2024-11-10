package io.github.eappezo.soundary.services.user.api.dto;

import io.github.eappezo.soundary.core.persistence.Image;

public record ImageUploadResponse(
        String id
) {
    public static ImageUploadResponse from(Image image) {
        return new ImageUploadResponse(image.id().toString());
    }
}
