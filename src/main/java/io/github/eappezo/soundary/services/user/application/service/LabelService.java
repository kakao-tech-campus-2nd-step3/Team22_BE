package io.github.eappezo.soundary.services.user.application.service;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.user.application.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final LabelRepository labelRepository;

    @Transactional
    public void addLabel(Identifier userId, List<Label> labels) {
        labelRepository.deleteAllByUserId(userId);
        labelRepository.saveAll(userId, labels);
    }

    @Transactional
    public void deleteLabel(Identifier userId, Label label) {
        labelRepository.deleteLabel(userId, label);
    }

    @Transactional(readOnly = true)
    public List<Label> getUserLabelList(Identifier userId) {
        return labelRepository.findAllByUserId(userId);
    }
}
