package io.github.eappezo.soundary.services.music.endpoint.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicByMeDto;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicByOtherDto;
import io.github.eappezo.soundary.services.music.application.share.service.SharedMusicService;
import io.github.eappezo.soundary.services.music.endpoint.api.SharedMusicAPI;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.RetrieveSharedMusicByMeResponse;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.RetrieveSharedMusicByOtherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/shared-musics")
@RequiredArgsConstructor
public class SharedMusicController implements SharedMusicAPI {
    private final SharedMusicService sharedMusicService;

    @Override
    @GetMapping("/sent")
    public RetrieveSharedMusicByMeResponse retrieveSentSharedMusics(
            @AuthenticatedUser Identifier userId
    ) {
        List<SharedMusicByMeDto> sharedMusics = sharedMusicService.getSharedMusicFrom(userId);

        return RetrieveSharedMusicByMeResponse.from(sharedMusics);
    }

    @Override
    @GetMapping("/received")
    public RetrieveSharedMusicByOtherResponse retrieveReceivedSharedMusics(
            @AuthenticatedUser Identifier userId
    ) {
        List<SharedMusicByOtherDto> sharedMusics = sharedMusicService.getSharedMusicTo(userId);

        return RetrieveSharedMusicByOtherResponse.from(sharedMusics);
    }

    @Override
    @PostMapping("/received/{shared-music-id}/likes")
    public void likeSharedMusic(
            @AuthenticatedUser Identifier userId,
            @PathVariable(name = "shared-music-id") String rawSharedMusicId
    ) {
        sharedMusicService.likeMusic(userId, Identifier.fromString(rawSharedMusicId));
    }

    @Override
    @DeleteMapping("/received/{shared-music-id}/likes")
    public void unlikeSharedMusic(
            @AuthenticatedUser Identifier userId,
            @PathVariable(name = "shared-music-id") String rawSharedMusicId
    ) {
        sharedMusicService.unlikeMusic(userId, Identifier.fromString(rawSharedMusicId));
    }
}
