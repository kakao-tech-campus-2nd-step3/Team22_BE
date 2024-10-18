package io.github.eappezo.soundary.services.friend.application.service;

import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRetrieveSupport;
import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.domain.exception.AlreadySentRequestException;

import java.util.List;

import io.github.eappezo.soundary.services.friend.domain.exception.FriendLimitException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendRetrieveSupport friendRetrieveSupport;

    @Value("${app.max-friends-count}")
    private Long maxFriendsCount;

    @Transactional
    public void addFriend(FriendshipDTO friendship) {
        if (!userRepository.existsById(friendship.to())) {
            throw new UserNotFoundException();
        }
        if (!userRepository.existsById(friendship.from())) {
            throw new UserNotFoundException();
        }
        if (friendRepository.exists(friendship)) {
            throw new AlreadySentRequestException();
        }
        if (friendRepository.countFriends(friendship.from()) >= maxFriendsCount) {
            throw new FriendLimitException();
        }
        if (friendRepository.countFriends(friendship.to()) >= maxFriendsCount) {
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
    public List<FriendInfo> getFriendList(Identifier userId) {
        return friendRetrieveSupport.findFriends(userId);
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
