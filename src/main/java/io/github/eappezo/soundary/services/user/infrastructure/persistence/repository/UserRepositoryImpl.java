package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserEntity;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.UserEntityMapper;
import io.github.eappezo.soundary.services.user.application.UserRoleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;
    private final UserRoleManager userRoleManager;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {
        List<UserRole> roles = userRoleManager.getRolesOf(user.getIdentifier());
        if (!roles.equals(user.getRoles())) {
            userRoleManager.setRoles(user.getIdentifier(), user.getRoles());
        }
        UserEntity savedUser = jpaUserRepository.save(UserEntity.fromDomain(user));
        return userEntityMapper.mapToUser(savedUser, user.getRoles());
    }

    @Override
    public Optional<User> findById(Identifier userId) {
        List<UserRole> userRoles = userRoleManager.getRolesOf(userId);
        return jpaUserRepository.findById(userId.toString())
                .map(userEntity -> userEntityMapper.mapToUser(userEntity, userRoles));
    }
}
