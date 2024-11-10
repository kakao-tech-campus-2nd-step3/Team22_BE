package io.github.eappezo.soundary.services.user.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.core.persistence.Image;

@JsonNaming(SnakeCaseStrategy.class)
public record ImageUploadResponse(
        String uploadedImageUrl
) {
    public static ImageUploadResponse from(Image image) {
        return new ImageUploadResponse(
                String.format("https://api.soundary.kro.kr/api/v1/images/%s", image.id().toString())
        );
    }
}
