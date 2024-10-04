package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.SimpleTrackDto;
import io.github.eappezo.soundary.services.music.domain.Album;
import io.github.eappezo.soundary.services.music.domain.Artist;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.domain.Track;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Arrays;

@Entity(name = "tracks")
@IdClass(TrackEntityKey.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private MusicPlatform platform;

    @Id
    @Column(name = "platform_track_id")
    private String platformTrackId;

    @Column(name = "title")
    private String title;

    @Column(name = "album_title")
    private String albumTitle;

    @Column(name = "serialized_artists")
    private String artists;

    @Column(name = "album_cover_url")
    private String albumCoverUrl;

    @Column(name = "preview_mp3_url")
    private String previewMp3Url;

    @Column(name = "duration_in_seconds")
    private Long durationInSeconds;

    public static TrackEntity from(SimpleTrackDto track) {
        return new TrackEntity(
                track.platform(),
                track.id(),
                track.title(),
                track.album(),
                track.artists(),
                track.albumCoverUrl(),
                track.previewMp3Url(),
                track.durationInSeconds()
        );
    }

    public Track toDomain() {
        return Track.of(
                platform,
                Identifier.fromString(platformTrackId),
                title,
                Arrays.stream(artists.split(", ")).map(Artist::new).toList(),
                new Album(albumTitle, albumCoverUrl),
                previewMp3Url,
                Duration.ofSeconds(durationInSeconds)
        );
    }
}
