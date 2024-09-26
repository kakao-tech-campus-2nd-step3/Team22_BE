package io.github.eappezo.soundary.services.friend.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import io.github.eappezo.soundary.services.friend.application.dto.UserIdentifiers;
import io.github.eappezo.soundary.services.friend.domain.Friend;
import io.github.eappezo.soundary.services.friend.domain.key.FriendKey;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public void addFriend(UserIdentifiers userIdentifiers){
        User from = getUser(userIdentifiers.from());
        User to = getUser(userIdentifiers.to());

        friendRepository.save(new Friend(from.getIdentifier().toString(), to.getIdentifier().toString()));
    }

    public void denyFriendRequest(UserIdentifiers userIdentifiers){
        friendRepository.deleteById(getFriendKey(userIdentifiers));
    }

    private User getUser(Identifier identifier){
        return userRepository.findById(identifier).orElseThrow();
    }

    private FriendKey getFriendKey(UserIdentifiers userIdentifiers){
        return new FriendKey(userIdentifiers.from().toString(), userIdentifiers.to().toString());
    }
}
