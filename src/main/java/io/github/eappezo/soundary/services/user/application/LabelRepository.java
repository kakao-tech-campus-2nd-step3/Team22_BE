package io.github.eappezo.soundary.services.user.application;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.UserLabelEntity;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.key.UserLabelEntityKey;
import java.util.List;
import java.util.Optional;

public interface LabelRepository {

    Optional<UserLabelEntity> findById(UserLabelEntityKey userLabelEntityKey);

    void saveAll(List<UserLabelEntity> labelEntities);

    void deleteById(UserLabelEntityKey userLabelEntityKey);

    List<UserLabelEntity> findByUserId(Identifier userId);
}