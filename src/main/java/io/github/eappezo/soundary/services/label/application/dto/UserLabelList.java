package io.github.eappezo.soundary.services.label.application.dto;

import io.github.eappezo.soundary.services.label.domain.Label;
import io.github.eappezo.soundary.services.label.domain.UserLabelEntity;
import java.util.List;

public record UserLabelList(
    String userId,
    List<Label> labels
) {

    public static UserLabelList from(List<UserLabelEntity> userLabelEntities) {
        return new UserLabelList(
            userLabelEntities.get(0).getUserId(),
            userLabelEntities.stream().map(UserLabelEntity::getLabel).toList()
        );
    }
}
