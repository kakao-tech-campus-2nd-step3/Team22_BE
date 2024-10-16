package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.UserRole;
import io.github.eappezo.soundary.services.user.application.UserRoleManager;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.UserRoleEntity;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.key.UserRoleEntityKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRoleManagerImpl implements UserRoleManager {
    private final JpaUserRoleRepository jpaUserRoleRepository;

    @Override
    public void appendRole(Identifier userId, UserRole role) {
        jpaUserRoleRepository.save(UserRoleEntity.of(userId, role));
    }

    @Override
    public void removeRole(Identifier userId, UserRole role) {
        jpaUserRoleRepository.deleteById(UserRoleEntityKey.of(userId, role));
    }

    @Override
    public void setRoles(Identifier userId, List<UserRole> roles) {
        jpaUserRoleRepository.deleteAllByUserId(userId.toString());
        jpaUserRoleRepository.saveAll(roles.stream().map(role -> UserRoleEntity.of(userId, role)).toList());
    }

    @Override
    public List<UserRole> getRolesOf(Identifier userId) {
        return jpaUserRoleRepository
                .findAllByUserId(userId.toString())
                .stream()
                .map(UserRoleEntity::getRole)
                .toList();
    }

    @Override
    public UserRole getHighestPriorityRole(Identifier userId) {
        return jpaUserRoleRepository.findAllByUserIdOrderByRolePriorityDesc(userId.toString())
                .stream()
                .map(UserRoleEntity::getRole)
                .findFirst()
                .orElse(null);
    }
}
