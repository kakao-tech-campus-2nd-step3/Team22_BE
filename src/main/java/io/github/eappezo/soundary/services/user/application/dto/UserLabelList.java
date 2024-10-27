package io.github.eappezo.soundary.services.user.application.dto;

import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.core.persistence.infrastructure.UserLabelEntity;
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
