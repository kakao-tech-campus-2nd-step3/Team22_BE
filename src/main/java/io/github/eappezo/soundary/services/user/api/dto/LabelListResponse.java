package io.github.eappezo.soundary.services.user.api.dto;

import io.github.eappezo.soundary.core.user.Label;

import java.util.List;

public record LabelListResponse(
        List<Label> labels
) {
    public static LabelListResponse from(List<Label> labels) {
        return new LabelListResponse(labels);
    }
}
