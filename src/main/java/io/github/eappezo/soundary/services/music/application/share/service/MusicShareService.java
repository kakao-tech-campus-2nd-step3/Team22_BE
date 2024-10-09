package io.github.eappezo.soundary.services.music.application.share.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.friend.FriendChecker;
import io.github.eappezo.soundary.services.music.domain.PlatformTrackId;
import io.github.eappezo.soundary.services.music.application.share.MusicShareSupport;
import io.github.eappezo.soundary.services.music.domain.SharedMusic;
import io.github.eappezo.soundary.services.music.domain.Track;
import io.github.eappezo.soundary.services.music.domain.TrackRepository;
import io.github.eappezo.soundary.services.music.domain.exception.NotFriendException;
import io.github.eappezo.soundary.services.music.domain.exception.TrackNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicShareService {
    private final FriendChecker friendChecker;
    private final TrackRepository trackRepository;
    private final MusicShareSupport musicShareSupport;

    @Transactional
    public SharedMusic shareMusic(
            Identifier fromUserId,
            List<Identifier> toUserIds,
            PlatformTrackId platformTrackId,
            String comment
    ) {
        if (!friendChecker.isFriendWith(fromUserId, toUserIds)) {
            throw new NotFriendException();
        }
        Track track = trackRepository
                .findByPlatformTrackId(platformTrackId)
                .orElseThrow(TrackNotFoundException::new);
        return musicShareSupport.shareTrack(fromUserId, toUserIds, track, comment);
    }
}
