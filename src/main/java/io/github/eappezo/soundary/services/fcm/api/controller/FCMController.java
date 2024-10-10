package io.github.eappezo.soundary.services.fcm.api.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
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
public class FCMController implements FCMAPI{
    private final FCMService fcmService;

    @Override
    @PostMapping("/token")
    public ResponseEntity<String> getToken(@AuthenticatedUser Identifier userId, @Valid @RequestBody PostTokenReq token){
        return ResponseEntity.ok(fcmService.getToken(userId, token.getToken()));
    }

    @Override
    @PostMapping("/send")
    public ResponseEntity<String> sendPushNotification(@AuthenticatedUser Identifier userId, @Valid @RequestBody PostTokenReq token, @RequestParam String title, @RequestParam String body) throws FirebaseMessagingException {
        fcmService.sendMessage(token.getToken(), title, body);
        return ResponseEntity.ok("푸시 알림 전송 성공");
    }
}
