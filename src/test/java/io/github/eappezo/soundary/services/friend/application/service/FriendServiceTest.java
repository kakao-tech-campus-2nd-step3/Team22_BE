package io.github.eappezo.soundary.services.friend.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import io.github.eappezo.soundary.advice.persistence.TransactionalPersistenceOperationGateway;
import io.github.eappezo.soundary.core.async.AsyncAdvice;
import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.NotificationSender;
import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRetrieveSupport;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.domain.exception.AlreadyFriendException;
import io.github.eappezo.soundary.services.friend.domain.exception.AlreadySentFriendRequestException;
import io.github.eappezo.soundary.services.friend.domain.exception.CannotRequestToMyselfException;
import io.github.eappezo.soundary.services.friend.domain.exception.FriendLimitException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {

    @InjectMocks
    private FriendService friendService;
    @Mock
    private AsyncAdvice asyncAdvice;
    @Spy
    private PersistenceOperationGateway persistenceOperationGateway = new TransactionalPersistenceOperationGateway();

    @Mock
    private FriendRepository friendRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FriendRetrieveSupport friendRetrieveSupport;

    @Mock
    private NotificationSender notificationSender;

    /*
    1. 없는 유저에게 친구요청을 보낸 경우
    2. 나에게 요청을 보낸 경우
    3. 이미 친구 요청을 보냈는데 다시 보내는 경우
    4. 맺어진 친구에게 요청을 보내는 경우
    5. 이미 친구가 20명이 있는데 또 친구요청을 보내는 경우
    6. 친구 수락을 했는데 상대가 친구 요청을 취소한 경우 (동시성 테스트? 근데 이게 중요한가)
     */

    FriendshipDTO friendShip;
    private Identifier userId;
    private Optional<Identifier> targetIdOp;

    @BeforeEach
    void setUp() {
        userId = Identifier.fromString("from");
        targetIdOp = Optional.of(Identifier.fromString("to"));
        friendShip = FriendshipDTO.of(userId, targetIdOp.get());

        ReflectionTestUtils.setField(friendService, "maxFriendsCount", 20L);

        lenient().when(userRepository.findIdByDisplayId(any())).thenReturn(targetIdOp);
        lenient().when(friendRepository.exists(friendShip)).thenReturn(false);
        lenient().when(friendRepository.exists(friendShip.reverse())).thenReturn(false);

        lenient().when(friendRepository.countFriends(any())).thenReturn(10);
    }

    @Test
    @DisplayName("없는 유저에게 친구요청을 보낸 경우")
    void addFriendNonExistUser() {
        //given
        targetIdOp = Optional.empty();
        when(userRepository.findIdByDisplayId(any())).thenReturn(targetIdOp);

        //when, then
        assertThrows(UserNotFoundException.class, () -> friendService.addFriend(userId, "to"));
    }

    @Test
    @DisplayName("나에게 요청을 보낸 경우")
    void addFriendMyself() {
        //given
        Optional<Identifier> userIdOp = Optional.of(userId);

        when(userRepository.findIdByDisplayId(any())).thenReturn(userIdOp);

        //when, then
        assertThrows(CannotRequestToMyselfException.class,
            () -> friendService.addFriend(userId, "me"));
    }

    @Test
    @DisplayName("이미 친구 요청을 보냈는데 다시 보내는 경우")
    void addFriendAlreadySent() {
        //given
        when(friendRepository.exists(friendShip)).thenReturn(true);
        when(friendRepository.exists(friendShip.reverse())).thenReturn(false);

        //when, then
        assertThrows(
            AlreadySentFriendRequestException.class, () -> friendService.addFriend(userId, "to"));
    }

    @Test
    @DisplayName("맺어진 친구에게 요청을 보내는 경우")
    void addFriendAlreadyFriend() {
        //given
        when(friendRepository.exists(friendShip)).thenReturn(true);
        when(friendRepository.exists(friendShip.reverse())).thenReturn(true);

        //when, then
        assertThrows(
            AlreadyFriendException.class, () -> friendService.addFriend(userId, "to"));
    }

    @Test
    @DisplayName("이미 친구가 최대일 때 또 친구요청을 보내는 경우")
    void addFriendAlready20Friend() {
        //given
        when(friendRepository.countFriends(any())).thenReturn(20);

        ReflectionTestUtils.setField(friendService, "maxFriendsCount", 20L);

        //when, then
        assertThrows(
            FriendLimitException.class, () -> friendService.addFriend(userId, "to"));
    }
}