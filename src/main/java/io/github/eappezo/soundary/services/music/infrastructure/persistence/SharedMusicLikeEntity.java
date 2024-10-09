package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "shared_music_like")
@IdClass(SharedMusicLikeEntityKey.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SharedMusicLikeEntity {
    @Id
    private String sharedMusicId;

    @Id
    private String likedUserId;
}
