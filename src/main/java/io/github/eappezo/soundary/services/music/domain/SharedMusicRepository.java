package io.github.eappezo.soundary.services.music.domain;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.Optional;

public interface SharedMusicRepository {

    Optional<SharedMusic> findById(Identifier id);

}
