package io.github.eappezo.soundary.services.user.api.dto;

import io.github.eappezo.soundary.services.user.application.dto.UserLabelList;

public record LabelListResponse(
    UserLabelList userLabelList
) {
    public static LabelListResponse from(UserLabelList userLabelList){
        return new LabelListResponse(userLabelList);
    }
}
