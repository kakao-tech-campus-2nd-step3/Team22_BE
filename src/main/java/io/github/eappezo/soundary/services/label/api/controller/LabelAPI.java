package io.github.eappezo.soundary.services.label.api.controller;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.label.api.dto.LabelAddRequest;
import io.github.eappezo.soundary.services.label.api.dto.LabelDeleteRequest;
import io.github.eappezo.soundary.services.label.api.dto.LabelListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "유저 라벨 설정 API", description = "사용자의 취향 음악 종류를 설정합니다.")
public interface LabelAPI {

    @Operation(summary = "유저 라벨 추가", description = "사용자의 음악 취향을 설정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "유저 라벨 추가 성공")
    })
    ResponseEntity<Void> addLabel(
        @Parameter(hidden = true) Identifier userId, LabelAddRequest labelAddRequest
    );

    @Operation(summary = "유저 라벨 삭제", description = "유저가 설정한 라벨을 삭제한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "유저 라벨 삭제 성공")
    })
    ResponseEntity<Void> deleteLabel(
        @Parameter(hidden = true) Identifier userId, LabelDeleteRequest labelDeleteRequest
    );

    @Operation(summary = "라벨 목록 조회", description = "유저가 설정한 라벨 목록을 조회한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저 라벨 목록 조회 성공")
    })
    ResponseEntity<LabelListResponse> getLabelList(
        @Parameter(hidden = true) Identifier userId
    );

}
