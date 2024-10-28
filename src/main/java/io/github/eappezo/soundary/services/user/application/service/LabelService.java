package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.application.LabelRepository;
import io.github.eappezo.soundary.services.user.application.dto.UserLabelList;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntityKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;

    public void addLabel(Identifier userId, List<Label> labels) {
        labelRepository.saveAll(userId, labels);
    }

    public void deleteLabel(Identifier userId, Label label) {
        labelRepository.deleteLabel(userId, label);
    }

    public List<Label> getUserLabelList(Identifier userId) {
        return labelRepository.findByUserId(userId);
    }
}
