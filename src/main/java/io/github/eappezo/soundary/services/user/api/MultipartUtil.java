package io.github.eappezo.soundary.services.user.api;

import io.github.eappezo.soundary.core.exception.common.CannotUploadImageException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartUtil {
    public static Image toImage(Identifier savedUserId, MultipartFile file) {
        if (
                file.getContentType() != null &&
                !file.getContentType().startsWith("image/")
        ) {
            throw new CannotUploadImageException();
        }
        try {
            return new Image(
                    null,
                    savedUserId,
                    file.getContentType(),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new CannotUploadImageException();
        }
    }

    public static ResponseEntity<byte[]> toResponse(Image image) {
        return ResponseEntity.ok()
                .header("Content-Type", image.contentType())
                .body(image.image());
    }
}
