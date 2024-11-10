package io.github.eappezo.soundary.core.persistence;

import io.github.eappezo.soundary.core.identification.Identifier;

public class Image {
    private final Identifier id;
    private final Identifier savedUserId;
    private final String contentType;
    private final byte[] image;

    public Image(
            Identifier id,
            Identifier savedUserId,
            String contentType,
            byte[] image
    ) {
        this.id = id;
        this.savedUserId = savedUserId;
        this.image = image;
        this.contentType = contentType;
    }

    public Identifier id() {
        return id;
    }

    public Identifier savedUserId() {
        return savedUserId;
    }

    public byte[] image() {
        return image;
    }

    public String contentType() {
        return contentType;
    }
}
