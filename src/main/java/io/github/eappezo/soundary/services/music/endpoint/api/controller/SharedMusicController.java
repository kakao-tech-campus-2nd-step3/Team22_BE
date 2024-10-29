package io.github.eappezo.soundary.services.music.endpoint.api.controller;

import io.github.eappezo.soundary.core.Page;
import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.exception.common.NotAuthorizedException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.ReceivedSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SentSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicQueryCondition;
import io.github.eappezo.soundary.services.music.application.share.service.SharedMusicService;
import io.github.eappezo.soundary.services.music.endpoint.api.SharedMusicAPI;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.PagedRetrieveReceivedSharedMusicResponse;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.PagedRetrieveSentSharedMusicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate,
            @RequestParam(name = "only-exposured", defaultValue = "false") Boolean onlyExposured,
            @RequestParam(name = "shared-by", required = false) Identifier fromUser
    ) {
        if (fromUser != null && !fromUser.equals(userId) && !onlyExposured) {
            throw new NotAuthorizedException();
        }
        if (fromUser == null) {
            fromUser = userId;
        }
        SharedMusicQueryCondition condition = new SharedMusicQueryCondition(
                page,
                size,
                onlyExposured,
                startDate,
                endDate
        );
        Page<SentSharedMusicDto> sharedMusics = sharedMusicService.getSentSharedMusic(fromUser, condition);

        return PagedRetrieveSentSharedMusicResponse.from(sharedMusics);
    }

    @Override
    @GetMapping("/received")
    public PagedRetrieveReceivedSharedMusicResponse retrieveReceivedSharedMusics(
            @AuthenticatedUser Identifier userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "start-date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam(name = "end-date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate
    ) {
        SharedMusicQueryCondition condition = new SharedMusicQueryCondition(
                page,
                size,
                true,
                startDate,
                endDate
        );
        Page<ReceivedSharedMusicDto> sharedMusics = sharedMusicService.getReceivedSharedMusic(
                userId,
                condition
        );
        return PagedRetrieveReceivedSharedMusicResponse.from(sharedMusics);
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
