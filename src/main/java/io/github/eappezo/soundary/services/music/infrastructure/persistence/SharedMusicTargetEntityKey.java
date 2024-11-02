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
public class SharedMusicTargetEntityKey {
    private String targetUserId;
    private String sharedMusicId;

    public static SharedMusicTargetEntityKey of(Identifier targetUserId, Identifier sharedMusicId) {
        return new SharedMusicTargetEntityKey(targetUserId.toString(), sharedMusicId.toString());
    }
}
