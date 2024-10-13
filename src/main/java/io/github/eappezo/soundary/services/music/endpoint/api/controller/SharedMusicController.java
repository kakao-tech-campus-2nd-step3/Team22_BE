package io.github.eappezo.soundary.services.music.endpoint.api.controller;

import io.github.eappezo.soundary.core.Page;
import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.SentSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.ReceivedSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SentSharedMusicQueryCondition;
import io.github.eappezo.soundary.services.music.application.share.service.SharedMusicService;
import io.github.eappezo.soundary.services.music.endpoint.api.SharedMusicAPI;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.PagedRetrieveSentSharedMusicResponse;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.RetrieveReceivedSharedMusicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/shared-musics")
@RequiredArgsConstructor
public class SharedMusicController implements SharedMusicAPI {
    private final SharedMusicService sharedMusicService;

    @Override
    @GetMapping("/sent")
    public PagedRetrieveSentSharedMusicResponse retrieveSentSharedMusics(
            @AuthenticatedUser Identifier userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "start-date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam(name = "end-date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate
    ) {
        SentSharedMusicQueryCondition condition = new SentSharedMusicQueryCondition(
                page,
                size,
                startDate,
                endDate
        );
        Page<SentSharedMusicDto> sharedMusics = sharedMusicService.getSentSharedMusic(userId, condition);
        return PagedRetrieveSentSharedMusicResponse.from(sharedMusics);
    }

    @Override
    @GetMapping("/received")
    public RetrieveReceivedSharedMusicResponse retrieveReceivedSharedMusics(
            @AuthenticatedUser Identifier userId
    ) {
        List<ReceivedSharedMusicDto> sharedMusics = sharedMusicService.getReceivedSharedMusic(userId);

        return RetrieveReceivedSharedMusicResponse.from(sharedMusics);
    }

    @Override
    @PostMapping("/received/{shared-music-id}/likes")
    public void likeSharedMusic(
            @AuthenticatedUser Identifier userId,
            @PathVariable(name = "shared-music-id") Identifier shareMusicId
    ) {
        sharedMusicService.likeMusic(userId, shareMusicId);
    }

    @Override
    @DeleteMapping("/received/{shared-music-id}/likes")
    public void unlikeSharedMusic(
            @AuthenticatedUser Identifier userId,
            @PathVariable(name = "shared-music-id") Identifier sharedMusicId
    ) {
        sharedMusicService.unlikeMusic(userId, sharedMusicId);
    }
}
