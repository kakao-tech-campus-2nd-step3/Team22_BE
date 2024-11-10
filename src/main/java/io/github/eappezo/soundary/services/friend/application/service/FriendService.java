package io.github.eappezo.soundary.services.friend.application.service;

import io.github.eappezo.soundary.core.async.AsyncAdvice;
import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.NotificationSender;
import io.github.eappezo.soundary.core.notification.NotificationType;
import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRetrieveSupport;
import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.domain.exception.AlreadyFriendException;
import io.github.eappezo.soundary.services.friend.domain.exception.AlreadySentFriendRequestException;
import io.github.eappezo.soundary.services.friend.domain.exception.CannotRequestToMyselfException;
import io.github.eappezo.soundary.services.friend.domain.exception.FriendLimitException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final AsyncAdvice asyncAdvice;
    private final PersistenceOperationGateway persistenceOperationGateway;

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendRetrieveSupport friendRetrieveSupport;

    private final NotificationSender notificationSender;

    @Value("${app.max-friends-count}")
    private Long maxFriendsCount;

    public void addFriend(Identifier userId, String targetDisplayId) {
        FriendResult result = persistenceOperationGateway.executeOperation(() -> {
            Identifier targetUserId = userRepository
                    .findIdByDisplayId(targetDisplayId)
                    .orElseThrow(UserNotFoundException::new);
            if (userId.equals(targetUserId)) {
                throw new CannotRequestToMyselfException();
            }
            FriendshipDTO friendship = FriendshipDTO.of(userId, targetUserId);
            FriendshipStatus status = getFriendshipStatus(friendship);

            if (friendRepository.exists(friendship)) {
                if (status == FriendshipStatus.ACCEPT) {
                    throw new AlreadyFriendException();
                }
                throw new AlreadySentFriendRequestException();
            }
            if (isFriendLimit(userId, targetUserId)) {
                throw new FriendLimitException();
            }
            friendRepository.save(friendship);
            return new FriendResult(targetUserId, status);
        });

        asyncAdvice.runAsync(() -> notificationSender.notice(
                result.notificationType(),
                userId,
                result.targetUserId()
        ));
    }

    @Transactional
    public void rejectFriendRequest(FriendshipDTO friendship) {
        friendRepository.delete(friendship);
    }

    @Transactional
    public void deleteFriend(FriendshipDTO friendship) {
        friendRepository.delete(friendship);
        friendRepository.delete(friendship.reverse());
    }

    @Transactional(readOnly = true)
    public List<FriendInfo> getFriendList(Identifier userId, List<Label> labels) {
        return friendRetrieveSupport.findFriends(userId, labels);
    }

    @Transactional(readOnly = true)
    public List<FriendRequestInfo> getSentRequests(Identifier fromUserId) {
        return friendRetrieveSupport.findSentRequests(fromUserId);
    }

    @Transactional(readOnly = true)
    public List<FriendRequestInfo> getReceivedRequests(Identifier toUserId) {
        return friendRetrieveSupport.findReceivedRequests(toUserId);
    }

    private boolean isFriendLimit(Identifier fromUserId, Identifier targetUserId) {
        return friendRepository.countFriends(fromUserId) >= maxFriendsCount;
    }

    private enum FriendshipStatus {
        SEND,
        ACCEPT
    }

    private FriendshipStatus getFriendshipStatus(FriendshipDTO friendship) {
        return friendRepository.exists(friendship.reverse())
                ? FriendshipStatus.ACCEPT
                : FriendshipStatus.SEND;
    }

    private record FriendResult(
            Identifier targetUserId,
            FriendshipStatus status
    ) {
        public NotificationType notificationType() {
            return status == FriendshipStatus.ACCEPT
                    ? NotificationType.ACCEPTED_FRIEND_REQUEST
                    : NotificationType.RECEIVED_FRIEND_REQUEST;
        }
    }
}
