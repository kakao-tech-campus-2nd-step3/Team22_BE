package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntityKey;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.user.application.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LabelRepositoryImpl implements LabelRepository {
    private final JpaLabelRepository jpaLabelRepository;

    @Override
    public void saveAll(Identifier userId, List<Label> labels) {
        List<UserLabelEntity> userLabelEntities = labels
                .stream()
                .map(label -> new UserLabelEntity(
                        userId.toString(),
                        label
                ))
                .toList();
        jpaLabelRepository.saveAll(userLabelEntities);
    }

    @Override
    public void deleteLabel(Identifier userId, Label label) {
        jpaLabelRepository.deleteById(UserLabelEntityKey.of(userId, label));
    }

    @Override
    public List<Label> findAllByUserId(Identifier userId) {
        return jpaLabelRepository
                .findAllByUserId(userId.toString())
                .stream()
                .map(UserLabelEntity::getLabel)
                .toList();
    }
}
