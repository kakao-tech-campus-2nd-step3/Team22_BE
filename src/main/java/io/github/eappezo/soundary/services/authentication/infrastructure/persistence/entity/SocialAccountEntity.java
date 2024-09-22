package io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.authentication.domain.SocialAccount;
import io.github.eappezo.soundary.services.authentication.domain.SocialPlatform;
import io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity.key.SocialAccountEntityKey;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@IdClass(SocialAccountEntityKey.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccountEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private SocialPlatform platform;

    @Id
    @Column(name = "social_id")
    private String socialId;

    @Column(name = "user_id")
    private String userId;

    public SocialAccount toDomain() {
        return new SocialAccount(platform, socialId, Identifier.fromString(userId));
    }

    public static SocialAccountEntity from(SocialAccount account) {
        return new SocialAccountEntity(account.platform(), account.socialId(), account.userId().toString());
    }
}
