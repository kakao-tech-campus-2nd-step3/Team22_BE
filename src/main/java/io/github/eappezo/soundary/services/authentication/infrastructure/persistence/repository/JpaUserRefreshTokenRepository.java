package io.github.eappezo.soundary.services.authentication.infrastructure.persistence.repository;

import io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity.UserRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRefreshTokenRepository extends JpaRepository<UserRefreshTokenEntity, String> {
}
