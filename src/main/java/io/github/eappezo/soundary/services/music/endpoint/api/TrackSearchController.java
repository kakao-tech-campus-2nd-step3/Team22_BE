package io.github.eappezo.soundary.services.music.endpoint.api;

import io.github.eappezo.soundary.services.music.application.service.TrackSearchService;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.Track;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.SearchTrackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrackSearchController implements TrackSearchAPI {
    private final TrackSearchService musicSearchService;

    @Override
    @GetMapping("/api/v1/tracks")
    public SearchTrackResponse search(
            @RequestParam(value = "platform", defaultValue = "SPOTIFY") String platform,
            @RequestParam("query") String query
    ) {
        List<Track> tracks = musicSearchService.search(
                MusicPlatform.fromString(platform),
                query
        );
        return SearchTrackResponse.from(tracks);
    }
}
