package io.github.eappezo.soundary.services.music.application.share;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.SharedMusic;

import java.util.List;

public interface SharedMusicRetrieveSupport {

    List<SharedMusic> getSharedMusicFrom(Identifier userId);

    List<SharedMusic> getSharedMusicTo(Identifier userId);

}
