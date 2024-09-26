package io.github.eappezo.soundary.services.friend.application;

import io.github.eappezo.soundary.services.friend.domain.Friend;
import io.github.eappezo.soundary.services.friend.domain.key.FriendKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, FriendKey> {

}
