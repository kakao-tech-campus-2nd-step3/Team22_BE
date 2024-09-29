package io.github.eappezo.soundary.services.music.endpoint.worker;

import io.github.eappezo.soundary.services.music.application.service.MusicPlatformAuthenticationRefreshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MusicPlatformAuthenticationRefresher {
    private final MusicPlatformAuthenticationRefreshService refreshService;

    @Async
    @Scheduled(fixedDelay = 55 * 60 * 1000)
    public void refreshAll() {
        refreshService.refreshAll();
    }
}
