package io.github.eappezo.soundary.services.music.endpoint.worker;

import io.github.eappezo.soundary.services.music.application.share.service.SharedMusicStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SharedMusicStatisticsRefresher {
    private final SharedMusicStatisticsService sharedMusicStatisticsService;

    @Async
    @Scheduled(fixedDelay = 55 * 60 * 1000)
    public void refresh() {
        sharedMusicStatisticsService.refreshStatistics();
    }
}
