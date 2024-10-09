package io.github.eappezo.soundary.services.music.endpoint.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.service.SharedMusicService;
import io.github.eappezo.soundary.services.music.domain.SharedMusic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/shared-musics")
@RequiredArgsConstructor
public class SharedMusicController {
    private final SharedMusicService sharedMusicService;

    @GetMapping("/sent")
    public void getSentSharedMusics(@AuthenticatedUser Identifier userId) {
        List<SharedMusic> sharedMusics = sharedMusicService.getSharedMusicFrom(userId);
    }

    @GetMapping("/received")
    public void getReceivedSharedMusics(@AuthenticatedUser Identifier userId) {
        List<SharedMusic> sharedMusics = sharedMusicService.getSharedMusicTo(userId);
    }

    @PostMapping("/received/{rawSharedMusicId}/likes")
    public void likeSharedMusic(
            @AuthenticatedUser Identifier userId,
            @PathVariable String rawSharedMusicId
    ) {
        sharedMusicService.likeMusic(userId, Identifier.fromString(rawSharedMusicId));
    }

    @DeleteMapping("/received/{rawSharedMusicId}/likes")
    public void unlikeSharedMusic(
            @AuthenticatedUser Identifier userId,
            @PathVariable String rawSharedMusicId
    ) {
        sharedMusicService.unlikeMusic(userId, Identifier.fromString(rawSharedMusicId));
    }
}
