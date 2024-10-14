package io.github.eappezo.soundary.services.label.api.dto;

import io.github.eappezo.soundary.services.label.application.dto.UserLabelList;

public record LabelListResponse(
    UserLabelList userLabelList
) {
    public static LabelListResponse from(UserLabelList userLabelList){
        return new LabelListResponse(userLabelList);
    }
}
