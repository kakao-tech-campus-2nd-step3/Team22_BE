package io.github.eappezo.soundary.services.user.domain;

import io.github.eappezo.soundary.core.persistence.infrastructure.BaseEntity;
import io.github.eappezo.soundary.services.friend.domain.key.FriendKey;
import io.github.eappezo.soundary.services.user.domain.key.UserKey;
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
@IdClass(UserKey.class)
public class User extends BaseEntity {
    @Id
    private String UserId;

}
