package io.github.eappezo.soundary.services.friend.domain;

import io.github.eappezo.soundary.core.persistence.infrastructure.BaseEntity;
import io.github.eappezo.soundary.services.friend.domain.key.FriendKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FriendKey.class)
public class Friend extends BaseEntity {
    @Id
    private String fromUserId;

    @Id
    private String toUserId;
}
