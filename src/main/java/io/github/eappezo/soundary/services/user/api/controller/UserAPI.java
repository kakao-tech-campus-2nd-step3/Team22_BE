package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.services.user.api.dto.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "사용자 API", description = "사용자 정보를 관리합니다.")
public interface UserAPI {

    @Operation(summary = "사용자 검색", description = "displayId로 사용자를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 검색 성공")
    })
    UserInfoResponse getUserInfo(@RequestParam(name = "displayId") String displayId);

}
