package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.services.user.application.LeavedUserRepository;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.LeavedUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LeavedUserRepositoryImpl implements LeavedUserRepository {
    private final JpaLeavedUserEntity jpaLeavedUserEntity;

    @Override
    public void save(User leavedUser) {
        jpaLeavedUserEntity.save(LeavedUserEntity.fromDomain(leavedUser));
    }
}
