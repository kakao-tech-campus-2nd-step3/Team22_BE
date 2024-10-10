package io.github.eappezo.soundary.services.fcm.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.fcm.api.dto.PostTokenReq;
import io.github.eappezo.soundary.services.fcm.application.service.FCMService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pushAlarm")
@RequiredArgsConstructor
public class FCMController {
    private final FCMService fcmService;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@AuthenticatedUser Identifier userId, @Valid @RequestBody PostTokenReq token){
        return ResponseEntity.ok(fcmService.getToken(userId, token.getToken()));
    }
}
