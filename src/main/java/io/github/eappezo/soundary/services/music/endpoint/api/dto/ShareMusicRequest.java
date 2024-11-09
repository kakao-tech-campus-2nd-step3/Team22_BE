package io.github.eappezo.soundary.services.music.endpoint.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.eappezo.soundary.core.exception.common.InvalidRequestPayloadException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.PlatformTrackId;
import io.github.eappezo.soundary.services.music.domain.TrackIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.util.List;

public record ShareMusicRequest(
        @JsonProperty("target_user_ids")
        List<String> rawTargetUserIds,

        @Schema(nullable = true)
        @Nullable PlatformTrackIdentifierDto track,

        @Schema(nullable = true)
        @JsonProperty("track_id")
        @Nullable String rawTrackId,
        String comment
) {
    public ShareMusicRequest {
        if (rawTargetUserIds.isEmpty()) {
            throw new InvalidRequestPayloadException("target_user_ids must not be empty");
        }
        if (track == null && rawTrackId == null) {
            throw new InvalidRequestPayloadException("track or track_id must be provided");
        }
        if (track != null && rawTrackId != null) {
            throw new InvalidRequestPayloadException("track and track_id cannot be provided at the same time");
        }
    }

    private record PlatformTrackIdentifierDto(
            MusicPlatform platform,
            @JsonProperty("platform_track_id") String rawPlatformTrackId
    ) {
        public PlatformTrackId platformTrackId() {
            return new PlatformTrackId(platform, rawPlatformTrackId);
        }
    }

    public TrackIdentifier trackIdentifier() {
        if (track != null) {
            return TrackIdentifier.from(track.platformTrackId());
        }
        return TrackIdentifier.from(Identifier.fromString(rawTrackId));
    }

    public List<Identifier> targetUserIds() {
        return rawTargetUserIds.stream()
                .map(Identifier::fromString)
                .toList();
    }
}
