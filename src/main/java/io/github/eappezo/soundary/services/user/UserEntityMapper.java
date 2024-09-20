package io.github.eappezo.soundary.services.user;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserEntity;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEntityMapper {
    public User mapToUser(UserEntity user, List<UserRole> roles) {
        return new User(
                Identifier.fromString(user.getId()),
                user.getDisplayId(),
                user.getNickname(),
                user.getDescription(),
                user.getProfileImageUrl(),
                roles,
                user.getCreatedAt()
        );
    }
}
