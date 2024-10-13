package io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity.key;

import io.github.eappezo.soundary.services.authentication.domain.SocialPlatform;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SocialAccountEntityKey implements Serializable {
    private SocialPlatform platform = SocialPlatform.KAKAO;
    private String socialId = "";

    public static SocialAccountEntityKey of(SocialPlatform platform, String socialId) {
        return new SocialAccountEntityKey(platform, socialId);
    }
}