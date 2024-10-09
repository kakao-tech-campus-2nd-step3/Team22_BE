package io.github.eappezo.soundary.services.user.infrastructure.persistence.entity;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.key.UserRoleEntityKey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_roles")
@IdClass(UserRoleEntityKey.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRoleEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    public static UserRoleEntity of(Identifier userId, UserRole role) {
        return new UserRoleEntity(userId.toString(), role);
    }
}
