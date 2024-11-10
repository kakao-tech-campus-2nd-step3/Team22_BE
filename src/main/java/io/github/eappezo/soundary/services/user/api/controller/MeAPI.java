package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.api.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "내 정보 관리 API", description = "나의 정보 조회, 수정 및 탈퇴를 관리합니다.")
public interface MeAPI {

    @Operation(summary = "내 정보 조회", description = "내 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공")
    })
    UserInfoResponse getMyInfo(@Parameter(hidden = true) Identifier userId);

    @Operation(summary = "내 정보 초기화", description = "초기 내 정보를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회 성공")
    })
    void initializeUser(
            @Parameter(hidden = true) Identifier userId,
            @RequestBody UserInfoInitializeRequest request
    );

    @Operation(summary = "내 정보 수정", description = "내 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 수정 성공")
    })
    UserUpdateResponse updateMyInfo(
            @Parameter(hidden = true) Identifier userId,
            @RequestBody UserUpdateRequest userUpdateRequest
    );

    @Operation(summary = "디바이스 토큰 갱신", description = "디바이스 토큰을 갱신합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "디바이스 토큰 갱신 성공")
    })
    void updateDeviceToken(
            @Parameter(hidden = true) Identifier userId,
            @RequestBody UpdateDeviceRequest request
    );

    @Operation(summary = "탈퇴", description = "탈퇴합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 탈퇴 성공")
    })
    void quit(@Parameter(hidden = true) Identifier userId);

}
