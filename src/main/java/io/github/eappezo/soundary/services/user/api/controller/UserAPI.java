package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.api.dto.UserInfoResponse;
import io.github.eappezo.soundary.services.user.api.dto.UserUpdateRequest;
import io.github.eappezo.soundary.services.user.api.dto.UserUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "사용자 관리 API", description = "사용자 정보 조회, 수정 및 탈퇴를 관리합니다.")
public interface UserAPI {

    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공")
    })
    ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticatedUser Identifier userId);

    @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 수정 성공")
    })
    ResponseEntity<UserUpdateResponse> updateUserInfo(
            @AuthenticatedUser Identifier userId,
            UserUpdateRequest userUpdateRequest
    );

    @Operation(summary = "사용자 탈퇴", description = "사용자를 탈퇴시킵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 탈퇴 성공")
    })
    ResponseEntity<Object> quitUser(@AuthenticatedUser Identifier userId);
}
