package io.github.eappezo.soundary.services.friend.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    6. 친구가 20명이 있는 상대에게 또 친구요청을 보내는 경우 // <- 이건 근데 할 수 있는거 아닌가?
    7. 친구 수락을 했는데 상대가 친구 요청을 취소한 경우 (동시성 테스트? 근데 이게 중요한가)
     */

    @Test
    @DisplayName("없는 유저에게 친구요청을 보낸 경우")
    void addFriendNonExistUser() {
        //given
        Optional<Identifier> nullId = Optional.empty();
        Identifier userId = Identifier.fromString("from");
        when(userRepository.findIdByDisplayId(any())).thenReturn(nullId);

        //when, then
        assertThrows(UserNotFoundException.class, () -> friendService.addFriend(userId, "to"));
    }

    @Test
    @DisplayName("나에게 요청을 보낸 경우")
    void addFriendMyself(){
        //given
        Identifier userId = Identifier.fromString("me");
        Optional<Identifier> userIdOp = Optional.of(userId);

        when(userRepository.findIdByDisplayId(any())).thenReturn(userIdOp);

        //when, then
        assertThrows(CannotRequestToMyselfException.class, () -> friendService.addFriend(userId, "me"));
    }

    @Test
    @DisplayName("이미 친구 요청을 보냈는데 다시 보내는 경우")
    void addFriendAlreadySent(){
        //given
        Identifier userId = Identifier.fromString("from");
        Optional<Identifier> targetIdOp = Optional.of(Identifier.fromString("to"));

        when(userRepository.findIdByDisplayId(any())).thenReturn(targetIdOp);
        FriendshipDTO friendShip = FriendshipDTO.of(userId, targetIdOp.get());
        when(friendRepository.exists(friendShip)).thenReturn(true);
        when(friendRepository.exists(friendShip.reverse())).thenReturn(false);

        //when, then
        assertThrows(
            AlreadySentFriendRequestException.class, () -> friendService.addFriend(userId, "to"));
    }

    @Test
    @DisplayName("맺어진 친구에게 요청을 보내는 경우")
    void addFriendAlreadyFriend(){
        //given
        Identifier userId = Identifier.fromString("from");
        Optional<Identifier> targetIdOp = Optional.of(Identifier.fromString("to"));

        when(userRepository.findIdByDisplayId(any())).thenReturn(targetIdOp);
        FriendshipDTO friendShip = FriendshipDTO.of(userId, targetIdOp.get());
        when(friendRepository.exists(friendShip)).thenReturn(true);
        when(friendRepository.exists(friendShip.reverse())).thenReturn(true);

        //when, then
        assertThrows(
            AlreadyFriendException.class, () -> friendService.addFriend(userId, "to"));
    }

    @Test
    @DisplayName("이미 친구가 최대일 때 또 친구요청을 보내는 경우")
    void addFriendAlready20Friend(){
        //given
        Identifier userId = Identifier.fromString("from");
        Optional<Identifier> targetIdOp = Optional.of(Identifier.fromString("to"));

        when(userRepository.findIdByDisplayId(any())).thenReturn(targetIdOp);
        when(friendRepository.exists(any())).thenReturn(false);
        when(friendRepository.countFriends(any())).thenReturn(20);

        ReflectionTestUtils.setField(friendService, "maxFriendsCount", 20L);

        //when, then
        assertThrows(
            FriendLimitException.class, () -> friendService.addFriend(userId, "to"));
    }

    @Test
    void getFriendList() {

    }

    @Test
    void getSentRequests() {
    }

    @Test
    void getReceivedRequests() {
    }
}