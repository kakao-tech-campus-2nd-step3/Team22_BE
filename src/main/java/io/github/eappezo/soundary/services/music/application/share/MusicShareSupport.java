package io.github.eappezo.soundary.services.music.application.share;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.SharedMusic;
import io.github.eappezo.soundary.services.music.domain.Track;

import java.util.List;

public interface MusicShareSupport {

    SharedMusic shareTrack(
            Identifier fromUserId,
            List<Identifier> toUserIds,
            Track track,
            String comment
    );

}
