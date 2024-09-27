package io.github.eappezo.soundary.services.friend.application;

import io.github.eappezo.soundary.services.friend.domain.Friend;
import io.github.eappezo.soundary.services.friend.domain.key.FriendKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, FriendKey> {

    List<Friend> findByFromUserId(String fromUserId);

    List<Friend> findByToUserId(String toUserId);
}
