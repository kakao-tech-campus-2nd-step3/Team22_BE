package io.github.eappezo.soundary.services.music.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SharedMusicTargetEntityKey {
    private String sharedMusicId;
    private String targetUserId;
}
