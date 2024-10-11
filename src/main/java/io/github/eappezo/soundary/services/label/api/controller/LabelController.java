package io.github.eappezo.soundary.services.label.api.controller;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.label.api.dto.LabelAddRequest;
import io.github.eappezo.soundary.services.label.api.dto.LabelDeleteRequest;
import io.github.eappezo.soundary.services.label.application.dto.UserLabelDto;
import io.github.eappezo.soundary.services.label.application.service.LabelService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/labels")
@RequiredArgsConstructor
public class LabelController implements LabelAPI{

    private final LabelService labelService;

    @Override
    @PostMapping()
    public ResponseEntity<Void> addLabel(Identifier userId, LabelAddRequest labelAddRequest) {
        labelService.addLabel(UserLabelDto.from(userId, labelAddRequest));
        return ResponseEntity.created(URI.create("api/v1/labels" + userId)).build();
    }

    @Override
    @DeleteMapping()
    public ResponseEntity<Void> deleteLabel(Identifier userId, LabelDeleteRequest labelDeleteRequest) {
        labelService.deleteLabel(userId, labelDeleteRequest.label());
        return ResponseEntity.noContent().build();
    }


}
