package io.github.eappezo.soundary.services.music.domain;

import io.github.eappezo.soundary.core.identification.Identifier;

public interface SharedMusicRepository {

    boolean exists(Identifier id);

    default boolean notExists(Identifier id) {
        return !exists(id);
    }

}
