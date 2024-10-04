package io.github.eappezo.soundary.services.music.domain;

import io.github.eappezo.soundary.core.identification.Identifier;
import jakarta.annotation.Nullable;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class Track {
    private final MusicPlatform platform;
    private final Identifier platformTrackId;
    private final String title;
    private final Album album;
    private final List<Artist> artist;
    private String previewMp3Url;
    private final Duration duration;

    public static Track of(
            MusicPlatform platform,
            Identifier id,
            String title,
            List<Artist> artist,
            Album album,
            String previewMp3Url,
            Duration duration
    ) {
        return new Track(platform, id, title, artist, album, previewMp3Url, duration);
    }

    public static Track of(
            MusicPlatform platform,
            Identifier id,
            String title,
            List<Artist> artist,
            Album album,
            Duration duration
    ) {
        return new Track(platform, id, title, artist, album, duration);
    }

    private Track(
            MusicPlatform platform,
            Identifier platformTrackId,
            String title,
            List<Artist> artist,
            Album album,
            String previewMp3Url,
            Duration duration
    ) {
        this.platform = platform;
        this.platformTrackId = platformTrackId;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.album = album;
        this.previewMp3Url = previewMp3Url;
    }

    private Track(
            MusicPlatform platform,
            Identifier platformTrackId,
            String title,
            List<Artist> artist,
            Album album,
            Duration duration
    ) {
        this.platform = platform;
        this.platformTrackId = platformTrackId;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.album = album;
        this.previewMp3Url = null;
    }

    public Identifier id() {
        return platformTrackId;
    }

    public MusicPlatform platform() {
        return platform;
    }

    public String title() {
        return title;
    }

    public List<Artist> artists() {
        return new LinkedList<>(artist);
    }

    @Nullable
    public String previewMp3Url() {
        return previewMp3Url;
    }

    public Duration duration() {
        return duration;
    }

    public String albumTitle() {
        return album.title();
    }

    public String albumCoverUrl() {
        return album.albumCoverUrl();
    }
}
