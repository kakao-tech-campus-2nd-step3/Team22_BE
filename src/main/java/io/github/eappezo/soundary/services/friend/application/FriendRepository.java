package io.github.eappezo.soundary.services.friend.application;

import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntityKey;
import java.util.List;
import java.util.Optional;

public interface FriendRepository {

    Optional<FriendEntity> findById(FriendEntityKey friendKey);

    void save(FriendEntity friend);

    void deleteById(FriendEntityKey friendKey);

    List<FriendEntity> findByFromUserId(String fromUserId);

    List<FriendEntity> findByToUserId(String toUserId);

    List<FriendEntity> findFriend(String fromUserId);

    List<FriendEntity> findReceivedRequest(String toUserId);

    List<FriendEntity> findSentRequest(String fromUserId);
}
