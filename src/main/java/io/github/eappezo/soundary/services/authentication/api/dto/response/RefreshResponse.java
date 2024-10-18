package io.github.eappezo.soundary.services.authentication.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.eappezo.soundary.services.authentication.application.RefreshResultDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RefreshResponse(
        String accessToken,
        @Schema(
                nullable = true,
                description = "리프레시 토큰의 남은 기간이 전체 기간의 25% 이하일 경우 새로운 리프레시 토큰을 발급합니다.",
                example = "null"
        )
        @Nullable String refreshToken,
        Long expiresIn
) {
    public static RefreshResponse from(RefreshResultDto userDto) {
        return new RefreshResponse(
                userDto.accessToken(),
                userDto.refreshToken(),
                userDto.expiresIn()
        );
    }
}
