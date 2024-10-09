package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Entity(name = "shared_music_target")
@IdClass(SharedMusicTargetEntity.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class SharedMusicTargetEntity {
    @Id
    private String sharedMusicId;

    @Id
    private String targetUserId;
}
