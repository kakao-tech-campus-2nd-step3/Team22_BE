package io.github.eappezo.soundary.services.friend.application;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;

import java.util.List;

public interface FriendRetrieveSupport {

    List<FriendInfo> findFriends(Identifier userId);

    List<FriendRequestInfo> findSentRequests(Identifier userId);

    List<FriendRequestInfo> findReceivedRequests(Identifier userId);

}
