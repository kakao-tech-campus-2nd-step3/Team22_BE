package io.github.eappezo.soundary.services.friend.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipInfo;
import io.github.eappezo.soundary.services.friend.domain.Friend;
import io.github.eappezo.soundary.services.friend.domain.exception.FriendsAPIFailedException;
import io.github.eappezo.soundary.services.friend.domain.key.FriendKey;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addFriend(FriendshipDTO friendshipDTO) {
        User from = getUser(friendshipDTO.from());
        User to = getUser(friendshipDTO.to());
        if (friendRepository.findById(getFriendKey(friendshipDTO)).isPresent()) {
            throw new FriendsAPIFailedException();
        }
        friendRepository.save(
            new Friend(from.getIdentifier().toString(), to.getIdentifier().toString()));
    }

    public void rejectFriendRequest(FriendshipDTO friendshipDTO) {
        friendRepository.deleteById(getFriendKey(friendshipDTO));
    }

    @Transactional
    public void deleteFriend(FriendshipDTO friendshipDTO) {
        friendRepository.deleteById(getFriendKey(friendshipDTO));
        friendRepository.deleteById(
            new FriendKey(friendshipDTO.to().toString(), friendshipDTO.from().toString()));
    }

    public FriendshipInfo getFriend(FriendshipDTO friendshipDTO) {
        return FriendshipInfo.from(
            friendRepository.findById(getFriendKey(friendshipDTO)).orElseThrow());
    }

    @Transactional
    public List<FriendInfo> getFriendList(Identifier fromUserId) {
        List<Friend> friendList = friendRepository.findByFromUserId(fromUserId.toString());
        List<FriendInfo> friendInfoList = new ArrayList<>();

        for (Friend it : friendList) {
            if (isFriend(it)) {
                friendInfoList.add(
                    FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
            }
        }

        return friendInfoList;
    }

    public List<FriendInfo> getFriendListWithSelfJoin(Identifier fromUserId){
        List<Friend> friendList = friendRepository.findFriend(fromUserId.toString());
        List<FriendInfo> friendInfoList = new ArrayList<>();
        for (Friend it : friendList) {
            friendInfoList.add(
                    FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
        }

        return  friendInfoList;
    }

    @Transactional
    public List<FriendInfo> getSentRequestList(Identifier fromUserId) {
        List<Friend> friendList = friendRepository.findByFromUserId(fromUserId.toString());
        List<FriendInfo> friendInfoList = new ArrayList<>();

        for (Friend it : friendList) {
            if (!isFriend(it)) {
                friendInfoList.add(
                    FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
            }
        }

        return friendInfoList;
    }

    @Transactional
    public List<FriendInfo> getSentRequestWithSelfJoin(Identifier fromUserId){
        List<Friend> friendList = friendRepository.findSentRequest(fromUserId.toString());
        List<FriendInfo> friendInfoList = new ArrayList<>();
        for (Friend it : friendList) {
            friendInfoList.add(
                FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
        }

        return  friendInfoList;
    }

    @Transactional
    public List<FriendInfo> getRequestReceivedList(Identifier toUserId) {
        List<Friend> friendList = friendRepository.findByToUserId(toUserId.toString());
        List<FriendInfo> friendInfoList = new ArrayList<>();

        for (Friend it : friendList) {
            if (!isFriend(it)) {
                friendInfoList.add(
                    FriendInfo.from(getUser(Identifier.fromString(it.getFromUserId()))));
            }
        }

        return friendInfoList;
    }

    @Transactional
    public List<FriendInfo> getRequestReceivedWithSelfJoin(Identifier toUserId){
        List<Friend> friendList = friendRepository.findReceivedRequest(toUserId.toString());
        List<FriendInfo> friendInfoList = new ArrayList<>();
        for (Friend it : friendList) {
            friendInfoList.add(
                FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
        }

        return  friendInfoList;
    }

    private boolean isFriend(Friend friend) {
        return friendRepository.findById(
                new FriendKey(friend.getToUserId(), friend.getFromUserId()))
            .isPresent();
    }

    private User getUser(Identifier identifier) {
        return userRepository.findById(identifier).orElseThrow();
    }

    private FriendKey getFriendKey(FriendshipDTO friendshipDTO) {
        return new FriendKey(friendshipDTO.from().toString(), friendshipDTO.to().toString());
    }
}
