package io.github.eappezo.soundary.services.music.endpoint.api.controller;

import io.github.eappezo.soundary.core.authentication.AuthenticatedUser;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.service.MusicShareService;
import io.github.eappezo.soundary.services.music.domain.SharedMusic;
import io.github.eappezo.soundary.services.music.endpoint.api.MusicShareAPI;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.ShareMusicRequest;
import io.github.eappezo.soundary.services.music.endpoint.api.dto.ShareMusicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/shared-musics")
@RequiredArgsConstructor
public class MusicShareController implements MusicShareAPI {
    private final MusicShareService musicShareService;

    @Override
    @PostMapping
    public ShareMusicResponse shareMusic(
            @AuthenticatedUser Identifier userId,
            @RequestBody ShareMusicRequest request
    ) {
        SharedMusic sharedMusic = musicShareService.shareMusic(
                userId,
                request.targetUserIds(),
                request.platformTrackId(),
                request.comment()
        );
        return ShareMusicResponse.from(sharedMusic);
    }
}
