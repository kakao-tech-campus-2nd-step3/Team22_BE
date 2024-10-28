package io.github.eappezo.soundary.services.music.application.share;

import io.github.eappezo.soundary.core.Page;
import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.List;

public interface SharedMusicRetrieveSupport {

    Page<SentSharedMusicDto> getSentSharedMusic(
            Identifier userId,
            SharedMusicQueryCondition condition
    );

    Page<ReceivedSharedMusicDto> getReceivedSharedMusic(
            Identifier userId,
            SharedMusicQueryCondition condition
    );

}
