package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.PlatformTrackId;

import java.util.List;

public record ShareMusicRequest(
        @JsonProperty("target_user_ids") List<String> rawTargetUserIds,
        TrackIdentifierDto track,
        String comment
) {
    private record TrackIdentifierDto(
            MusicPlatform platform,
            @JsonProperty("platform_track_id") String rawPlatformTrackId
    ) {
        public PlatformTrackId platformTrackId() {
            return new PlatformTrackId(platform, rawPlatformTrackId);
        }
    }

    public PlatformTrackId platformTrackId() {
        return track.platformTrackId();
    }

    public List<Identifier> targetUserIds() {
        return rawTargetUserIds.stream()
                .map(Identifier::fromString)
                .toList();
    }
}
