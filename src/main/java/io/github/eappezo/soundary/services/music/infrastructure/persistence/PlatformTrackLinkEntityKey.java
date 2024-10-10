package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.PlatformTrackId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlatformTrackLinkEntityKey implements Serializable {
    private MusicPlatform platform;
    private String platformTrackId;

    public static PlatformTrackLinkEntityKey from(PlatformTrackId platformTrackId) {
        return new PlatformTrackLinkEntityKey(
                platformTrackId.platform(),
                platformTrackId.platformTrackId()
        );
    }
}
