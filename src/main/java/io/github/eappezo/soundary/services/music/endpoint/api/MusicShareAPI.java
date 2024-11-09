package io.github.eappezo.soundary.services.music.endpoint.api;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.ShareMusicRequest;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.ShareMusicResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "음악 공유 API", description = "음악을 공유합니다.")
public interface MusicShareAPI {

    @Operation(
            summary = "음악 공유",
            description = "음악을 공유합니다. track field 와 track_id field 중 하나만 제공해야 합니다."
    )

    ShareMusicResponse shareMusic(
            @Parameter(hidden = true) Identifier userId,
            @RequestBody ShareMusicRequest request
    );

}
