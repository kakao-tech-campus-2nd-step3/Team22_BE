package io.github.eappezo.soundary.services.friend.application;

import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<FriendEntity, FriendKey> {

    List<FriendEntity> findByFromUserId(String fromUserId);

    List<FriendEntity> findByToUserId(String toUserId);
}
