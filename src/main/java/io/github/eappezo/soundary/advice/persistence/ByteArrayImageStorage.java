package io.github.eappezo.soundary.advice.persistence;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.identification.IdentifierGenerator;
import io.github.eappezo.soundary.core.persistence.Image;
import io.github.eappezo.soundary.core.persistence.ImageStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ByteArrayImageStorage implements ImageStorage {
    private final IdentifierGenerator identifierGenerator;
    private final JpaByteArrayImageStorage jpaByteArrayImageStorage;

    @Override
    public Image save(Image image) {
        Identifier id = identifierGenerator.generate();
        ImageEntity savedImaged = jpaByteArrayImageStorage
                .save(ImageEntity.from(id, image));
        return savedImaged.toDomain();
    }

    @Override
    public Optional<Image> load(Identifier id) {
        return jpaByteArrayImageStorage.findById(id.toString()).map(ImageEntity::toDomain);
    }
}
