package io.github.eappezo.soundary.services.label.application;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.label.domain.UserLabelEntity;
import io.github.eappezo.soundary.services.label.domain.UserLabelEntityKey;
import java.util.List;
import java.util.Optional;

public interface LabelRepository {

    Optional<UserLabelEntity> findById(UserLabelEntityKey userLabelEntityKey);

    void saveAll(List<UserLabelEntity> labelEntities);

    void deleteById(UserLabelEntityKey userLabelEntityKey);

    List<UserLabelEntity> findByUserId(Identifier userId);
}