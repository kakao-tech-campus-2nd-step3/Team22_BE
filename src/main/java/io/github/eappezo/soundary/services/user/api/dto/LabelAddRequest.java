package io.github.eappezo.soundary.services.user.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.eappezo.soundary.core.user.Label;

import java.util.List;

public record LabelAddRequest(
        @JsonProperty("labels")
        List<String> rawLabels
) {
    public List<Label> labels() {
        return rawLabels.stream()
                .map(label -> Label.from(label.toUpperCase()))
                .toList();
    }
}
