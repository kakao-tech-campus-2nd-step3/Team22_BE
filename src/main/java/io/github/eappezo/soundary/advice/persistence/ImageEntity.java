package io.github.eappezo.soundary.advice.persistence;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "images")
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    public static ImageEntity from(Identifier identifier, Image image) {
        String id = image.id() != null ? image.id().toString() : identifier.toString();
        return new ImageEntity(
                id,
                image.savedUserId().toString(),
                image.contentType(),
                image.image()
        );
    }

    public Image toDomain() {
        return new Image(
                Identifier.fromString(id),
                Identifier.fromString(userId),
                contentType,
                image
        );
    }
}
