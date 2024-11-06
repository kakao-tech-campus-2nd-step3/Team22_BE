package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.LeavedUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLeavedUserEntity extends JpaRepository<LeavedUserEntity, String> {
}
