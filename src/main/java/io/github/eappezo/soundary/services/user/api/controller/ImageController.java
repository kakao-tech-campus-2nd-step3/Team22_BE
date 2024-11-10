package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.Image;
import io.github.eappezo.soundary.services.user.api.MultipartUtil;
import io.github.eappezo.soundary.services.user.api.dto.ImageUploadResponse;
import io.github.eappezo.soundary.services.user.application.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController implements ImageAPI {
    private final ImageService imageService;

    @Override
    @PostMapping
    public ImageUploadResponse uploadImage(
            @AuthenticatedUser Identifier userId,
            @RequestParam(name = "image") MultipartFile image
    ) {
        Image uploadedImage = imageService.uploadImage(MultipartUtil.toImage(userId, image));

        return ImageUploadResponse.from(uploadedImage);
    }

    @Override
    @GetMapping("/{image-id}")
    public ResponseEntity<byte[]> getImage(
            @AuthenticatedUser Identifier userId,
            @PathVariable("image-id") Identifier imageId
    ) {
        Image image = imageService.getImage(imageId);
        return MultipartUtil.toResponse(image);
    }
}
