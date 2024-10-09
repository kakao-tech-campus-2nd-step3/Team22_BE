package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "platform_track_link")
@IdClass(PlatformTrackLinkEntityKey.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformTrackLinkEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private MusicPlatform platform;

    @Id
    @Column(name = "platform_track_id")
    private String platformTrackId;

    @Column(name = "track_id")
    private String trackId;
}
