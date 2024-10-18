package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.application.LabelRepository;
import io.github.eappezo.soundary.services.user.application.dto.UserLabelDto;
import io.github.eappezo.soundary.services.user.application.dto.UserLabelList;
import io.github.eappezo.soundary.services.user.domain.Label;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.UserLabelEntity;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.key.UserLabelEntityKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public void addLabel(UserLabelDto userLabelDto) {
        String userId = userLabelDto.userId();

        labelRepository.saveAll(
            userLabelDto.labels().stream()
                .map(label -> new UserLabelEntity(userId, label))
                .toList()
        );
    }

    public void deleteLabel(Identifier userId, String label) {

        labelRepository.deleteById(
            getUserLabelEntityKey(userId.toString(), Label.getLabel(label.toUpperCase())));
    }

    public UserLabelList getUserLabelList(Identifier userId) {
        return UserLabelList.from(labelRepository.findByUserId(userId));
    }

    private UserLabelEntityKey getUserLabelEntityKey(String userId, Label label) {
        return new UserLabelEntityKey(userId, label);
    }
}
