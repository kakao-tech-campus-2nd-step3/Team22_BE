package io.github.eappezo.soundary.services.music.domain;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class Track {
    private final String id;
    private final MusicPlatform platform;
    private final String title;
    private final Album album;
    private final List<Artist> artist;
    private String previewMp3Url = "";
    private final Duration duration;

    public static Track of(
            String id,
            MusicPlatform platform,
            String title,
            List<Artist> artist,
            Album album,
            String previewMp3Url,
            Duration duration
    ) {
        return new Track(id, platform, title, artist, album, previewMp3Url, duration);
    }

    public static Track of(
            String id,
            MusicPlatform platform,
            String title,
            List<Artist> artist,
            Album album,
            Duration duration
    ) {
        return new Track(id, platform, title, artist, album, duration);
    }

    private Track(
            String id,
            MusicPlatform platform,
            String title,
            List<Artist> artist,
            Album album,
            String previewMp3Url,
            Duration duration
    ) {
        this.id = id;
        this.platform = platform;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.album = album;
        this.previewMp3Url = previewMp3Url;
    }

    private Track(
            String id,
            MusicPlatform platform,
            String title,
            List<Artist> artist,
            Album album,
            Duration duration
    ) {
        this.id = id;
        this.platform = platform;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.album = album;
    }

    public String id() {
        return id;
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
