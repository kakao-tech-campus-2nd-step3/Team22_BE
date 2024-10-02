package io.github.eappezo.soundary.core.persistence.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "friends")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FriendKey.class)
public class FriendEntity extends BaseEntity {
    @Id
    private String fromUserId;

    @Id
    private String toUserId;
}
