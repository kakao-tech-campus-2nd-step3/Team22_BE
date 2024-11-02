package io.github.eappezo.soundary.services.user.application;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntityKey;
import io.github.eappezo.soundary.core.user.Label;

import java.util.List;
import java.util.Optional;

public interface LabelRepository {

    Optional<UserLabelEntity> findById(UserLabelEntityKey userLabelEntityKey);

    void saveAll(Identifier userId, List<Label> labels);

    void deleteLabel(Identifier userId, Label label);

    List<Label> findByUserId(Identifier userId);
}