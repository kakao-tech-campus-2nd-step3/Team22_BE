package io.github.eappezo.soundary.services.music.application.share.service;

import io.github.eappezo.soundary.core.async.AsyncAdvice;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.NotificationSender;
import io.github.eappezo.soundary.core.notification.NotificationType;
import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import io.github.eappezo.soundary.core.user.friend.FriendChecker;
import io.github.eappezo.soundary.services.music.application.share.MusicShareSupport;
import io.github.eappezo.soundary.services.music.domain.PlatformTrackId;
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
    private final AsyncAdvice asyncAdvice;
    private final PersistenceOperationGateway persistenceOperationGateway;

    private final FriendChecker friendChecker;
    private final TrackRepository trackRepository;
    private final MusicShareSupport musicShareSupport;

    private final NotificationSender notificationSender;

    public SharedMusic shareMusic(
            Identifier userId,
            List<Identifier> targetUserIds,
            PlatformTrackId platformTrackId,
            String comment
    ) {
        SharedMusic sharedMusic = persistenceOperationGateway.executeOperation(() -> {
            if (!friendChecker.isFriendWith(userId, targetUserIds)) {
                throw new NotFriendException();
            }
            Track track = trackRepository
                    .findByPlatformTrackId(platformTrackId)
                    .orElseThrow(TrackNotFoundException::new);
            return musicShareSupport.shareTrack(userId, targetUserIds, track, comment);
        });
        asyncAdvice.runAsync(() -> {
            notificationSender.notice(NotificationType.MUSIC_SHARED, userId, targetUserIds);
        });
        return sharedMusic;
    }
}
