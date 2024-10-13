package io.github.eappezo.soundary.services.music.application.share;

import io.github.eappezo.soundary.core.identification.Identifier;

public interface SharedMusicLikeSupport {

    void like(Identifier userId, Identifier sharedMusicId);

    void unlike(Identifier userId, Identifier sharedMusicId);

}
