package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.Album;
import io.github.eappezo.soundary.services.music.domain.Artist;
import io.github.eappezo.soundary.services.music.domain.Track;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Arrays;

@Entity(name = "tracks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TrackEntity {
    public static final String ARTISTS_DELIMITER = ", ";

    @Id
    @Column(name = "id")
    private String id;

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

    public Track toDomain() {
        return Track.of(
                Identifier.fromString(id),
                title,
                Arrays.stream(artists.split(", ")).map(Artist::new).toList(),
                new Album(albumTitle, albumCoverUrl),
                previewMp3Url,
                Duration.ofSeconds(durationInSeconds)
        );
    }
}
