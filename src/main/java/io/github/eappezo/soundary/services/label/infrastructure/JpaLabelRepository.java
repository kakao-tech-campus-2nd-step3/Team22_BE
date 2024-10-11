package io.github.eappezo.soundary.services.label.infrastructure;

import io.github.eappezo.soundary.services.label.domain.UserLabelEntity;
import io.github.eappezo.soundary.services.label.domain.UserLabelEntityKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLabelRepository extends JpaRepository<UserLabelEntity, UserLabelEntityKey> {

    List<UserLabelEntity> findByUserId(String userId);
}
