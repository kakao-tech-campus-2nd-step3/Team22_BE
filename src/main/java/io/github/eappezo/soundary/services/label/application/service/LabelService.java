package io.github.eappezo.soundary.services.label.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.label.application.LabelRepository;
import io.github.eappezo.soundary.services.label.application.dto.UserLabelDto;
import io.github.eappezo.soundary.services.label.domain.Label;
import io.github.eappezo.soundary.services.label.domain.UserLabelEntity;
import io.github.eappezo.soundary.services.label.domain.UserLabelEntityKey;
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

        labelRepository.deleteById(getUserLabelEntityKey(userId.toString(), Label.valueOf(label)));
    }

    private UserLabelEntityKey getUserLabelEntityKey(String userId, Label label) {
        return new UserLabelEntityKey(userId, label);
    }
}
