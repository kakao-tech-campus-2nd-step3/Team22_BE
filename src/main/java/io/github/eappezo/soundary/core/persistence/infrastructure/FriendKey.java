package io.github.eappezo.soundary.core.persistence.infrastructure;

import java.io.Serializable;

import io.github.eappezo.soundary.core.identification.Identifier;
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

    public static FriendKey of(Identifier fromUserId, Identifier toUserId) {
        return new FriendKey(fromUserId.toString(), toUserId.toString());
    }
}
