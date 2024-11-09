package io.github.eappezo.soundary.services.music.application.share.service;

import io.github.eappezo.soundary.core.async.AsyncAdvice;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.NotificationSender;
import io.github.eappezo.soundary.core.notification.NotificationType;
import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import io.github.eappezo.soundary.core.user.friend.FriendChecker;
import io.github.eappezo.soundary.services.music.application.share.MusicShareSupport;
import io.github.eappezo.soundary.services.music.domain.TrackIdentifier;
import io.github.eappezo.soundary.services.music.domain.*;
import io.github.eappezo.soundary.services.music.domain.exception.NotFriendException;
import io.github.eappezo.soundary.services.music.domain.exception.TrackNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            TrackIdentifier trackIdentifier,
            String comment
    ) {
        SharedMusic sharedMusic = persistenceOperationGateway.executeOperation(() -> {
            if (!friendChecker.isFriendWith(userId, targetUserIds)) {
                throw new NotFriendException();
            }
            Track track = trackRepository.findByTrackIdentifier(trackIdentifier)
                    .orElseThrow(TrackNotFoundException::new);
            return musicShareSupport.shareTrack(userId, targetUserIds, track, comment);
        });
        asyncAdvice.runAsync(() -> {
            notificationSender.notice(NotificationType.MUSIC_SHARED, userId, targetUserIds);
        });
        return sharedMusic;
    }
}
