package io.github.eappezo.soundary.services.label.api.dto;

import java.util.List;

public record LabelAddRequest(
    List<String> labels
) {

}
