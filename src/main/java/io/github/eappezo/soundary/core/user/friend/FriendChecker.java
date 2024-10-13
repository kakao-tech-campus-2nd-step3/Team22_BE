package io.github.eappezo.soundary.core.user.friend;

import io.github.eappezo.soundary.core.identification.Identifier;
import java.util.List;

public interface FriendChecker {

    boolean isFriendWith(Identifier userId, List<Identifier> friendIds);

    boolean isFriend(Identifier userId, Identifier friendId);

}
