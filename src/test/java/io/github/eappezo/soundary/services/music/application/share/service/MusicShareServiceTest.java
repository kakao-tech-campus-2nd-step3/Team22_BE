package io.github.eappezo.soundary.services.music.application.share.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import io.github.eappezo.soundary.advice.persistence.TransactionalPersistenceOperationGateway;
import io.github.eappezo.soundary.core.async.AsyncAdvice;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import io.github.eappezo.soundary.core.user.friend.FriendChecker;
import io.github.eappezo.soundary.services.music.application.share.MusicShareSupport;
import io.github.eappezo.soundary.services.music.domain.TrackIdentifier;
import io.github.eappezo.soundary.services.music.domain.TrackRepository;
import io.github.eappezo.soundary.services.music.domain.exception.NotFriendException;
import io.github.eappezo.soundary.services.music.domain.exception.TrackNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MusicShareServiceTest {

    Identifier userId = Identifier.fromString("user");
    @InjectMocks
    private MusicShareService musicShareService;
    @Mock
    private AsyncAdvice asyncAdvice;
    @Spy
    private PersistenceOperationGateway persistenceOperationGateway = new TransactionalPersistenceOperationGateway();
    @Mock
    private FriendChecker friendChecker;
    @Mock
    private TrackRepository trackRepository;
    @Mock
    private MusicShareSupport musicShareSupport;

    /*
    1. 친구가 아닌데 음악 공유 하려 하는 경우
    2. trackId가 잘못 들어온 경우(해당 트랙을 찾을 수 없는 경우)
     */
    @DisplayName("친구가 아닌데 음악 공유 하려 하는 경우")
    @Test
    void shareMusicToNotFriend() {
        //given
        List<Identifier> targetUserIds = new ArrayList<>();
        TrackIdentifier trackIdentifier = TrackIdentifier.from(Identifier.fromString("track"));
        String comment = "comment";

        when(friendChecker.isFriendWith(userId, targetUserIds)).thenReturn(false);
        //when,then
        assertThrows(NotFriendException.class,
            () -> musicShareService.shareMusic(userId, targetUserIds, trackIdentifier, comment));
    }

    @DisplayName("trackId가 잘못 들어온 경우(해당 트랙을 찾을 수 없는 경우)")
    @Test
    void shareWrongMusicTrack(){
        //given
        List<Identifier> targetUserIds = new ArrayList<>();
        TrackIdentifier trackIdentifier = TrackIdentifier.from(Identifier.fromString("track"));
        String comment = "comment";

        when(friendChecker.isFriendWith(userId, targetUserIds)).thenReturn(true);
        when(trackRepository.findByTrackIdentifier(trackIdentifier)).thenReturn(Optional.empty());

        //when, then
        assertThrows(TrackNotFoundException.class,
            () -> musicShareService.shareMusic(userId,targetUserIds,trackIdentifier, comment));

    }


}