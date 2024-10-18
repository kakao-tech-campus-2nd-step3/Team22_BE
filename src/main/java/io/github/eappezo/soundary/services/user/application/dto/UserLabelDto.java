package io.github.eappezo.soundary.services.user.application.dto;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.api.dto.LabelAddRequest;
import io.github.eappezo.soundary.services.user.domain.Label;
import java.util.List;

public record UserLabelDto(
    String userId,
    List<Label> labels
) {

    public static UserLabelDto from(Identifier userId, LabelAddRequest labelAddRequest) {
        return new UserLabelDto(
            userId.toString(),
            labelAddRequest.labels().stream()
                .map(label -> Label.getLabel(label.toUpperCase()))
                .toList()
        );
    }
}
