package io.github.eappezo.soundary.services.user.application.dto;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.api.dto.LabelAddRequest;
import io.github.eappezo.soundary.core.user.Label;
import java.util.List;

public record UserLabelDto(
    Identifier userId,
    List<Label> labels
) {

    public static UserLabelDto from(Identifier userId, LabelAddRequest labelAddRequest) {
        return new UserLabelDto(
            userId,
            labelAddRequest.rawLabels().stream()
                .map(label -> Label.from(label.toUpperCase()))
                .toList()
        );
    }
}
