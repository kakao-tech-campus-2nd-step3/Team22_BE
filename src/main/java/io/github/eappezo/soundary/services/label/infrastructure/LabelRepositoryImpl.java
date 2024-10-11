package io.github.eappezo.soundary.services.label.infrastructure;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.label.application.LabelRepository;
import io.github.eappezo.soundary.services.label.domain.UserLabelEntity;
import io.github.eappezo.soundary.services.label.domain.UserLabelEntityKey;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LabelRepositoryImpl implements LabelRepository {

    private final JpaLabelRepository jpaLabelRepository;

    @Override
    public Optional<UserLabelEntity> findById(UserLabelEntityKey userLabelEntityKey) {
        return jpaLabelRepository.findById(userLabelEntityKey);
    }

    @Override
    public void saveAll(List<UserLabelEntity> labelEntities) {
        jpaLabelRepository.saveAll(labelEntities);
    }

    @Override
    public void deleteById(UserLabelEntityKey userLabelEntityKey) {
        jpaLabelRepository.deleteById(userLabelEntityKey);
    }

    @Override
    public List<UserLabelEntity> findByUserId(Identifier userId) {
        return jpaLabelRepository.findByUserId(userId.toString());
    }
}
