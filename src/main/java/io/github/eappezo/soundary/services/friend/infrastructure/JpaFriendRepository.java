package io.github.eappezo.soundary.services.friend.infrastructure;

import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntityKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaFriendRepository extends JpaRepository<FriendEntity, FriendEntityKey> {

    List<FriendEntity> findByFromUserId(String fromUserId);

    List<FriendEntity> findByToUserId(String toUserId);

    @Query("SELECT t1 FROM friends as t1 JOIN friends as t2 ON t1.fromUserId = t2.toUserId AND t1.toUserId = t2.fromUserId WHERE t1.fromUserId = :fromUserId")
    List<FriendEntity> findFriend(String fromUserId);

    @Query("SELECT t1 FROM friends as t1 JOIN friends as t2 ON t1.fromUserId = t2.toUserId AND t1.toUserId != t2.fromUserId WHERE t1.toUserId = :toUserId")
    List<FriendEntity> findReceivedRequest(String toUserId);

    @Query("SELECT t1 FROM friends t1 JOIN friends t2 ON t1.fromUserId = t2.toUserId AND t1.toUserId != t2.fromUserId WHERE t1.fromUserId = :fromUserId")
    List<FriendEntity> findSentRequest(String fromUserId);

}
