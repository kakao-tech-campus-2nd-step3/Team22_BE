package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.UserLabelEntity;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.key.UserLabelEntityKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLabelRepository extends JpaRepository<UserLabelEntity, UserLabelEntityKey> {

    List<UserLabelEntity> findByUserId(String userId);
}
