package io.github.eappezo.soundary.services.fcm.api.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.fcm.api.dto.PostTokenReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "푸시 알림 API", description = "푸시 알림 토큰 관리 및 전송 기능을 제공합니다.")
public interface FCMAPI {

    @Operation(summary = "푸시 알림 토큰 생성", description = "사용자 푸시 알림 토큰을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "푸시 알림 토큰 생성 성공")
    })
    ResponseEntity<String> getToken(
            @AuthenticatedUser Identifier userId,
            @Valid PostTokenReq tokenRequest
    );

    @Operation(summary = "푸시 알림 전송", description = "특정 사용자에게 푸시 알림을 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "푸시 알림 전송 성공")
    })
    ResponseEntity<String> sendPushNotification(
            @AuthenticatedUser Identifier userId,
            @Valid PostTokenReq tokenRequest,
            String title,
            String body
    ) throws FirebaseMessagingException;
}
