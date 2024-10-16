package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.UserRoleEntity;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.key.UserRoleEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface JpaUserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleEntityKey> {

    List<UserRoleEntity> findAllByUserId(String userId);

    void deleteAllByUserId(String userId);

    List<UserRoleEntity> findAllByUserIdOrderByRolePriorityDesc(String userId);
}
