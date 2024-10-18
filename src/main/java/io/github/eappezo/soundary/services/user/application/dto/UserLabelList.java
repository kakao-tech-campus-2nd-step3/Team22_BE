package io.github.eappezo.soundary.services.user.application.dto;

import io.github.eappezo.soundary.services.user.domain.Label;
import io.github.eappezo.soundary.services.user.infrastructure.persistence.entity.UserLabelEntity;
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
