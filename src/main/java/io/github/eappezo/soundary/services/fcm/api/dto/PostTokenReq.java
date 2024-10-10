package io.github.eappezo.soundary.services.fcm.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostTokenReq {
    @NotBlank(message = "토큰을 입력해주세요")
    @Schema(description = "FCM token", example = "")
    String token;
}
