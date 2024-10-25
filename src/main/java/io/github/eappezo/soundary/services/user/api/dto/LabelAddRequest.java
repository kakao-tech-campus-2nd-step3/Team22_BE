package io.github.eappezo.soundary.services.user.api.dto;

import java.util.List;

public record LabelAddRequest(
    List<String> labels
) {

}
