package io.github.eappezo.soundary.services.authentication.infrastructure.persistence.repository;

import io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity.SocialAccountEntity;
import io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity.key.SocialAccountEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSocialAccountRepository extends JpaRepository<SocialAccountEntity, SocialAccountEntityKey> {
}
