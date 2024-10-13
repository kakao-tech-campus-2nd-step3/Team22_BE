package io.github.eappezo.soundary.services.music.domain;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.time.LocalDateTime;

public class SharedMusic {
    private final Identifier id;
    private final Identifier fromUserId;
    private final Track track;
    private final String comment;
    private final LocalDateTime sharedAt;

    public static SharedMusic of(
            Identifier id,
            Identifier fromUserId,
            Track track,
            String comment,
            LocalDateTime sharedAt
    ) {
        return new SharedMusic(
                id,
                fromUserId,
                track,
                comment,
                sharedAt
        );
    }

    private SharedMusic(
            Identifier id,
            Identifier fromUserId,
            Track track,
            String comment,
            LocalDateTime sharedAt
    ) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.track = track;
        this.comment = comment;
        this.sharedAt = sharedAt;
    }

    public Identifier id() {
        return id;
    }

    public Identifier fromUserId() {
        return fromUserId;
    }

    public Track track() {
        return track;
    }

    public String comment() {
        return comment;
    }

    public LocalDateTime sharedAt() {
        return sharedAt;
    }
}
