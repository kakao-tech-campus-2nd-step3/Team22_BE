package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "shared_music")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SharedMusicEntity extends BaseEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "from_user_id")
    private String fromUserId;

    @Column(name = "track_id")
    private String trackId;

    @Column(name = "comment")
    private String comment;

    public static SharedMusicEntity newSharedMusic(
            Identifier id,
            Identifier fromUserId,
            Identifier trackId,
            String comment
    ) {
        return new SharedMusicEntity(
                id.toString(),
                fromUserId.toString(),
                trackId.toString(),
                comment
        );
    }
}
