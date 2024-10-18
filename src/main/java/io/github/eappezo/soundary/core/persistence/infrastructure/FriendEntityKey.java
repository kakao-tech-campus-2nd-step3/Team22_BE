package io.github.eappezo.soundary.core.persistence.infrastructure;

import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FriendEntityKey implements Serializable {
    private String fromUserId;
    private String toUserId;

    public static FriendEntityKey from(FriendshipDTO friendship) {
        return new FriendEntityKey(
            friendship.from().toString(),
            friendship.to().toString()
        );
    }
}
