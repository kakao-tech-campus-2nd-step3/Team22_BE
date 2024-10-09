package io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.key;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRoleEntityKey {
    private String userId = "";
    private UserRole role = UserRole.PENDING;

    public static UserRoleEntityKey of(Identifier userId, UserRole role) {
        return new UserRoleEntityKey(userId.toString(), role);
    }
}
