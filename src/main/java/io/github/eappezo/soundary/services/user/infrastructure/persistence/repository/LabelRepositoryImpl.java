package io.github.eappezo.soundary.services.user.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.user.application.LabelRepository;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntityKey;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LabelRepositoryImpl implements LabelRepository {
    private final JpaLabelRepository jpaLabelRepository;
    private final JdbcTemplate jdbcTemplate;

    private static final String labelBatchSql = """
           INSERT IGNORE INTO user_labels (user_id, label, created_at)
           VALUES (?, ?, ?);
           """;

    @Override
    public Optional<UserLabelEntity> findById(UserLabelEntityKey userLabelEntityKey) {
        return jpaLabelRepository.findById(userLabelEntityKey);
    }

    @Override
    public void saveAll(Identifier userId, List<Label> labels) {
        String rawUserId = userId.toString();
        LocalDateTime now = LocalDateTime.now();
        List<Object[]> batchArgs = new ArrayList<>();
        for (Label label : labels) {
            batchArgs.add(new Object[]{rawUserId, label, now});
        }
        jdbcTemplate.batchUpdate(labelBatchSql, batchArgs);
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
