package io.github.eappezo.soundary.services.friend.application.service;

import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntityKey;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipInfo;
import io.github.eappezo.soundary.services.friend.domain.exception.FriendsAPIFailedException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FriendService {

    private FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addFriend(FriendshipDTO friendshipDTO) {
        User from = getUser(friendshipDTO.from());
        User to = getUser(friendshipDTO.to());
        if (friendRepository.findById(getFriendKey(friendshipDTO)).isPresent()) {
            throw new FriendsAPIFailedException();
        }
        friendRepository.save(
            new FriendEntity(from.getIdentifier().toString(), to.getIdentifier().toString()));
    }

    public void rejectFriendRequest(FriendshipDTO friendshipDTO) {
        friendRepository.deleteById(getFriendKey(friendshipDTO));
    }

    @Transactional
    public void deleteFriend(FriendshipDTO friendshipDTO) {
        friendRepository.deleteById(getFriendKey(friendshipDTO));
        friendRepository.deleteById(
            new FriendEntityKey(friendshipDTO.to().toString(), friendshipDTO.from().toString()));
    }

    public FriendshipInfo getFriend(FriendshipDTO friendshipDTO) {
        return FriendshipInfo.from(
            friendRepository.findById(getFriendKey(friendshipDTO)).orElseThrow());
    }

    public List<FriendInfo> getFriendList(Identifier fromUserId) {
        List<FriendEntity> friendList = friendRepository.findFriend(fromUserId.toString());
        List<FriendInfo> friendInfoList = new ArrayList<>();
        for (FriendEntity it : friendList) {
            friendInfoList.add(
                FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
        }

        return friendInfoList;
    }

    @Transactional
    public List<FriendInfo> getSentRequestList(Identifier fromUserId) {
        List<FriendEntity> friendList = friendRepository.findSentRequest(fromUserId.toString());
        List<FriendInfo> friendInfoList = new ArrayList<>();
        for (FriendEntity it : friendList) {
            friendInfoList.add(
                FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
        }

        return friendInfoList;
    }

    @Transactional
    public List<FriendInfo> getRequestReceived(Identifier toUserId) {
        List<FriendEntity> friendList = friendRepository.findReceivedRequest(toUserId.toString());
        List<FriendInfo> friendInfoList = new ArrayList<>();
        for (FriendEntity it : friendList) {
            friendInfoList.add(
                FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
        }

        return friendInfoList;
    }

    private User getUser(Identifier identifier) {
        return userRepository.findById(identifier).orElseThrow(UserNotFoundException::new);
    }

    private FriendEntityKey getFriendKey(FriendshipDTO friendshipDTO) {
        return new FriendEntityKey(friendshipDTO.from().toString(), friendshipDTO.to().toString());
    }
}
