package io.github.eappezo.soundary.services.music.domain;

import io.github.eappezo.soundary.core.identification.Identifier;
import jakarta.annotation.Nullable;

public class TrackIdentifier {
    private final Type type;
    @Nullable private Identifier internalId;
    @Nullable private PlatformTrackId platformTrackId;

    private TrackIdentifier(Identifier internalId) {
        this.type = Type.INTERNAL;
        this.internalId = internalId;
    }

    private TrackIdentifier(PlatformTrackId platformTrackId) {
        this.type = Type.PLATFORM;
        this.platformTrackId = platformTrackId;
    }

    public enum Type {
        INTERNAL,
        PLATFORM
    }

    public static TrackIdentifier from(Identifier id) {
        return new TrackIdentifier(id);
    }

    public static TrackIdentifier from(PlatformTrackId platformTrackId) {
        return new TrackIdentifier(platformTrackId);
    }

    public PlatformTrackId platformTrackId() {
        return platformTrackId;
    }

    public Identifier internalId() {
        return internalId;
    }

    public Type type() {
        return type;
    }
}
