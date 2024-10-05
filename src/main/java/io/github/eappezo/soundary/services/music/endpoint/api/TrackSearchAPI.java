package io.github.eappezo.soundary.services.music.endpoint.api;

import io.github.eappezo.soundary.services.music.endpoint.api.dto.SearchTrackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "음악 검색 API", description = "음악을 조회합니다.")
public interface TrackSearchAPI {

    @Operation(summary = "트랙 검색하기", description = "트랙을 검색합니다.")
    @ApiResponse(responseCode = "200", description = "검색 결과")
    SearchTrackResponse search(String platform, String query);

}
