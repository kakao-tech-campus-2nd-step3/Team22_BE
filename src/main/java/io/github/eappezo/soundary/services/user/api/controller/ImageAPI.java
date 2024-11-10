package io.github.eappezo.soundary.services.user.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.user.api.dto.ImageUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "이미지 API", description = "이미지 업로드 및 조회를 관리합니다.")
public interface ImageAPI {
    @Operation(summary = "이미지 업로드 API", description = "이미지를 업로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 업로드 성공")
    })
    ImageUploadResponse uploadImage(
            @Parameter(hidden = true) Identifier userId,
            @RequestParam("image") MultipartFile image
    );

    @Operation(summary = "이미지 조회 API", description = "이미지를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 조회 성공")
    })
    ResponseEntity<byte[]> getImage(
            @AuthenticatedUser Identifier userId,
            @PathVariable("image-id") Identifier imageId
    );
}
