package io.github.eappezo.soundary.services.music.endpoint.api;

import io.github.eappezo.soundary.services.music.endpoint.api.dto.MostLikedTracksResponse;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.MostSharedTracksResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "공유 음악 통계 API", description = "공유 음악의 통계를 조회합니다.")
public interface SharedMusicStatisticsAPI {

    MostSharedTracksResponse getMostSharedTracks();

    MostLikedTracksResponse getMostLikedTracks();

}
