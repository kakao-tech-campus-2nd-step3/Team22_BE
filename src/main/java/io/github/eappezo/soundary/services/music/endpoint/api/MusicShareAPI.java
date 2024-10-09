package io.github.eappezo.soundary.services.music.endpoint.api;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.ShareMusicRequest;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.ShareMusicResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "음악 공유 API", description = "음악을 공유합니다.")
public interface MusicShareAPI {

    ShareMusicResponse shareMusic(
            @Parameter(hidden = true) Identifier userId,
            @RequestBody ShareMusicRequest request
    );

}
