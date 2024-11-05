package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.user.api.dto.LabelAddRequest;
import io.github.eappezo.soundary.services.user.api.dto.LabelListResponse;
import io.github.eappezo.soundary.services.user.application.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/labels")
@RequiredArgsConstructor
public class LabelController implements LabelAPI {
    private final LabelService labelService;

    @Override
    @PostMapping
    public void addLabel(
            @AuthenticatedUser Identifier userId,
            @RequestBody LabelAddRequest request
    ) {
        labelService.addLabel(userId, request.labels());
    }

    @Override
    @DeleteMapping("/{label}")
    public void deleteLabel(
            @AuthenticatedUser Identifier userId,
            @PathVariable("label") String label
    ) {
        labelService.deleteLabel(userId, Label.from(label.toUpperCase()));
    }

    @Override
    @GetMapping
    public LabelListResponse getLabels(
            @AuthenticatedUser Identifier userId
    ) {
        return LabelListResponse.from(labelService.getUserLabelList(userId));
    }
}
