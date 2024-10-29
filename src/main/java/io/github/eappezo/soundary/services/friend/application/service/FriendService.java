package io.github.eappezo.soundary.services.friend.application.service;

import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRetrieveSupport;
import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.domain.exception.CannotRequestToMyselfException;
import io.github.eappezo.soundary.services.friend.domain.exception.CannotSentRequestException;
import io.github.eappezo.soundary.services.friend.domain.exception.FriendLimitException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendRetrieveSupport friendRetrieveSupport;

    @Value("${app.max-friends-count}")
    private Long maxFriendsCount;

    @Transactional
    public void addFriend(Identifier userId, String targetDisplayId) {
        Identifier targetUserId = userRepository
                .findIdByDisplayId(targetDisplayId)
                .orElseThrow(UserNotFoundException::new);
        if (userId.equals(targetUserId)) {
            throw new CannotRequestToMyselfException();
        }
        FriendshipDTO friendship = FriendshipDTO.of(userId, targetUserId);
        if (friendRepository.exists(friendship)) {
            throw new CannotSentRequestException();
        }
        if (friendRepository.countFriends(userId) >= maxFriendsCount) {
            throw new FriendLimitException();
        }
        if (friendRepository.countFriends(targetUserId) >= maxFriendsCount) {
            throw new FriendLimitException();
        }
        friendRepository.save(friendship);
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
}
