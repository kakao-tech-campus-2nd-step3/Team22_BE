package io.github.eappezo.soundary.services.friend.domain.key;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FriendKey implements Serializable {
    private String fromUserId;
    private String toUserId;
}
