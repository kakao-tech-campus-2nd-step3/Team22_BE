package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.exception.common.ResourceNotExistsException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.Image;
import io.github.eappezo.soundary.core.persistence.ImageStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageStorage imageStorage;

    @Transactional
    public Image uploadImage(Image image) {
        return imageStorage.save(image);
    }

    @Transactional(readOnly = true)
    public Image getImage(Identifier imageId) {
        return imageStorage.load(imageId).orElseThrow(ResourceNotExistsException::new);
    }
}
