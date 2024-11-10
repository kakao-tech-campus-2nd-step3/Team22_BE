package io.github.eappezo.soundary.core.persistence;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.Optional;

public interface ImageStorage {

    Image save(Image image);

    Optional<Image> load(Identifier id);

}
