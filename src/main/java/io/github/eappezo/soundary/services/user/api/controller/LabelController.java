package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.user.api.dto.LabelAddRequest;
import io.github.eappezo.soundary.services.user.api.dto.LabelListResponse;
import io.github.eappezo.soundary.services.user.application.dto.UserLabelDto;
import io.github.eappezo.soundary.services.user.application.service.LabelService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/labels")
@RequiredArgsConstructor
public class LabelController implements LabelAPI {
    private final LabelService labelService;

    @Override
    @PostMapping
    public void addLabel(
            Identifier userId,
            LabelAddRequest request
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
