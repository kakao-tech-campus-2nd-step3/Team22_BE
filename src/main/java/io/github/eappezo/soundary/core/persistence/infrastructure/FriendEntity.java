package io.github.eappezo.soundary.core.persistence.infrastructure;

import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
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
@IdClass(FriendEntityKey.class)
public class FriendEntity extends BaseEntity {
    @Id
    private String fromUserId;

    @Id
    private String toUserId;

    public static FriendEntity from(FriendshipDTO friendship) {
        return new FriendEntity(
            friendship.from().toString(),
            friendship.to().toString()
        );
    }
}
