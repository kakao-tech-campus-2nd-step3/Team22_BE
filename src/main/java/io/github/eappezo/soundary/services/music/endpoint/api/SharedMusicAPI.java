package io.github.eappezo.soundary.services.music.endpoint.api;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.RetrieveSentSharedMusicResponse;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.RetrieveReceivedSharedMusicResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "공유된 음악 확인 API", description = "공유된 음악을 확인합니다.")
public interface SharedMusicAPI {

    RetrieveSentSharedMusicResponse retrieveSentSharedMusics(
            @Parameter(hidden = true) Identifier userId
    );

    RetrieveReceivedSharedMusicResponse retrieveReceivedSharedMusics(
            @Parameter(hidden = true) Identifier userId
    );

    void likeSharedMusic(
            @Parameter(hidden = true) Identifier userId,
            @PathVariable(name = "shared-music-id") Identifier sharedMusicId
    );

    void unlikeSharedMusic(
            @Parameter(hidden = true) Identifier userId,
            @PathVariable(name = "shared-music-id")  Identifier sharedMusicId
    );

}
