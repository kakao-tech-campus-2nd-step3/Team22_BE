package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.identification.Identifier;
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
    @PostMapping()
    public ResponseEntity<Void> addLabel(Identifier userId, LabelAddRequest labelAddRequest) {
        labelService.addLabel(UserLabelDto.from(userId, labelAddRequest));
        return ResponseEntity.created(URI.create("api/v1/labels" + userId)).build();
    }

    @Override
    @DeleteMapping("/{label}")
    public ResponseEntity<Void> deleteLabel(Identifier userId,
        @PathVariable String label) {
        labelService.deleteLabel(userId, label);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping()
    public ResponseEntity<LabelListResponse> getLabelList(Identifier userId) {
        return ResponseEntity.ok(LabelListResponse.from(labelService.getUserLabelList(userId)));
    }

}
