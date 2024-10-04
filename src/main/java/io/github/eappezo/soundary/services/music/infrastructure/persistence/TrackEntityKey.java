package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TrackEntityKey implements Serializable {
    private MusicPlatform platform;
    private String platformTrackId;

    public static TrackEntityKey of(MusicPlatform platform, Identifier id) {
        return new TrackEntityKey(platform, id.toString());
    }
}
