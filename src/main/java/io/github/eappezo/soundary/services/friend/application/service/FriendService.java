package io.github.eappezo.soundary.services.friend.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntity;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.friend.api.dto.FriendRequest;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import io.github.eappezo.soundary.services.friend.domain.exception.FriendsAPIFailedException;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendKey;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public void addFriend(FriendshipDTO friendshipDTO){
        User from = getUser(friendshipDTO.from());
        User to = getUser(friendshipDTO.to());
        if(friendRepository.findById(getFriendKey(friendshipDTO)).isPresent()){
            throw new FriendsAPIFailedException();
        }
        friendRepository.save(new FriendEntity(from.getIdentifier().toString(), to.getIdentifier().toString()));
    }

    public void rejectFriendRequest(FriendshipDTO friendshipDTO){
        friendRepository.deleteById(getFriendKey(friendshipDTO));
    }

    public void deleteFriend(FriendRequest friendRequest){
        friendRepository.deleteById(getFriendKey(FriendshipDTO.from(friendRequest)));
        friendRepository.deleteById(new FriendKey(friendRequest.toUserId(), friendRequest.fromUserId()));
    }

    public List<FriendInfo> getFriendList(String fromUserId){
        List<FriendEntity> friendList = friendRepository.findByFromUserId(fromUserId);
        List<FriendInfo> friendInfoList = new ArrayList<>();

        for (FriendEntity it : friendList){
            if(isFriend(it)){
                friendInfoList.add(
                    FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
            }
        }

        return friendInfoList;
    }

    public List<FriendInfo> getSentRequestList(String fromUserId){
        List<FriendEntity> friendList = friendRepository.findByFromUserId(fromUserId);
        List<FriendInfo> friendInfoList = new ArrayList<>();

        for (FriendEntity it : friendList){
            if(!isFriend(it)){
                friendInfoList.add(
                    FriendInfo.from(getUser(Identifier.fromString(it.getToUserId()))));
            }
        }

        return friendInfoList;
    }

    public List<FriendInfo> getRequestReceivedList(String toUserId){
        List<FriendEntity> friendList = friendRepository.findByToUserId(toUserId);
        List<FriendInfo> friendInfoList = new ArrayList<>();

        for (FriendEntity it : friendList){
            if(!isFriend(it)){
                friendInfoList.add(
                    FriendInfo.from(getUser(Identifier.fromString(it.getFromUserId()))));
            }
        }

        return friendInfoList;
    }

    private boolean isFriend(FriendEntity friend){
        return friendRepository.findById(new FriendKey(friend.getToUserId(), friend.getFromUserId()))
            .isPresent();
    }

    private User getUser(Identifier identifier){
        return userRepository.findById(identifier).orElseThrow();
    }

    private FriendKey getFriendKey(FriendshipDTO friendshipDTO){
        return new FriendKey(friendshipDTO.from().toString(), friendshipDTO.to().toString());
    }
}
