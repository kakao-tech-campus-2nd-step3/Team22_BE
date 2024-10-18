package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import io.github.eappezo.soundary.core.identification.Identifier;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SharedMusicLikeEntityKey {
    private String sharedMusicId;
    private String likedUserId;

    public static SharedMusicLikeEntityKey of(Identifier sharedMusicId, Identifier likedUserId) {
        return new SharedMusicLikeEntityKey(sharedMusicId.toString(), likedUserId.toString());
    }
}
