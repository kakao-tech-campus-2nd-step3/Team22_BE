package io.github.eappezo.soundary.services.friend.application;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;

public interface FriendRepository {

    boolean exists(FriendshipDTO friendship);

    int countFriends(Identifier userId);

    void save(FriendshipDTO friendship);

    void delete(FriendshipDTO friendship);

}
