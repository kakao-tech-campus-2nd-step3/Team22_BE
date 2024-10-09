package io.github.eappezo.soundary.services.music.application.share;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.List;

public interface SharedMusicRetrieveSupport {

    List<SentSharedMusicDto> getSharedMusicFrom(Identifier userId);

    List<ReceivedSharedMusicDto> getSharedMusicTo(Identifier userId);

}
