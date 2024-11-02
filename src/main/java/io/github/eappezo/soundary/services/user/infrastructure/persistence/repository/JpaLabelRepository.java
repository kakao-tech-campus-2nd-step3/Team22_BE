package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaLabelRepository extends JpaRepository<UserLabelEntity, UserLabelEntityKey> {

    List<UserLabelEntity> findByUserId(String userId);
}
