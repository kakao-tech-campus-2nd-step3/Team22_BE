package io.github.eappezo.soundary.services.music.application.share;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.List;

public interface SharedMusicRetrieveSupport {

    List<SentSharedMusicDto> getSentSharedMusic(Identifier userId);

    List<ReceivedSharedMusicDto> getReceivedSharedMusic(Identifier userId);

}
