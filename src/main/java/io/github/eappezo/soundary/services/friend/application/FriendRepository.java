package io.github.eappezo.soundary.services.friend.application;

import io.github.eappezo.soundary.services.friend.domain.Friend;
import io.github.eappezo.soundary.services.friend.domain.key.FriendKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friend, FriendKey> {

    List<Friend> findByFromUserId(String fromUserId);

    List<Friend> findByToUserId(String toUserId);

    @Query("SELECT t1 FROM Friend t1 JOIN Friend t2 ON t1.fromUserId = t2.toUserId AND t1.toUserId = t2.fromUserId WHERE t1.fromUserId = :fromUserId")
    List<Friend> findFriend(String fromUserId);

    @Query("SELECT t1 FROM Friend t1 JOIN Friend t2 ON t1.fromUserId != t2.toUserId AND t1.toUserId = t2.fromUserId WHERE t1.toUserId = :toUserId")
    List<Friend> findReceivedRequest(String toUserId);

    @Query("SELECT t1 FROM Friend t1 JOIN Friend t2 ON t1.fromUserId != t2.toUserId AND t1.toUserId = t2.fromUserId WHERE t1.fromUserId = :fromUserId")
    List<Friend> findSentRequest(String fromUserId);
}
