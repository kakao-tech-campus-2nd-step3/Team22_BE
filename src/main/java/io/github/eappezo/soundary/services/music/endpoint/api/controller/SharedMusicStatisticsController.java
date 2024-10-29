package io.github.eappezo.soundary.services.music.endpoint.api.controller;

import io.github.eappezo.soundary.services.music.application.share.MostLikedTracksDto;
import io.github.eappezo.soundary.services.music.application.share.MostSharedTracksDto;
import io.github.eappezo.soundary.services.music.application.share.service.SharedMusicStatisticsService;
import io.github.eappezo.soundary.services.music.endpoint.api.SharedMusicStatisticsAPI;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.MostLikedTracksResponse;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.MostSharedTracksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shared-musics/statistics")
@RequiredArgsConstructor
public class SharedMusicStatisticsController implements SharedMusicStatisticsAPI {
    private final SharedMusicStatisticsService sharedMusicStatisticsService;

    @Override
    @GetMapping("/most-shared-tracks")
    public MostSharedTracksResponse getMostSharedTracks() {
        MostSharedTracksDto mostSharedTracks = sharedMusicStatisticsService.getMostSharedTracks();

        return MostSharedTracksResponse.from(mostSharedTracks);
    }

    @Override
    @GetMapping("/most-liked-tracks")
    public MostLikedTracksResponse getMostLikedTracks() {
        MostLikedTracksDto mostLikedTracks = sharedMusicStatisticsService.getMostLikedTracks();

        return MostLikedTracksResponse.from(mostLikedTracks);
    }
}
