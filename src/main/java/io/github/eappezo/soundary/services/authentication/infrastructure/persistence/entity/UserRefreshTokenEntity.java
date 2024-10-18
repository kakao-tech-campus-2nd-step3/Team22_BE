package io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.BaseAuditingEntity;
import io.github.eappezo.soundary.services.authentication.application.RefreshTokenDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_refresh_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRefreshTokenEntity extends BaseAuditingEntity {
    @Id
    private String userId;

    @Column(nullable = false)
    private String refreshToken;

    public static UserRefreshTokenEntity of(
            Identifier userId,
            RefreshTokenDto refreshToken
    ) {
        return new UserRefreshTokenEntity(
                userId.toString(),
                refreshToken.value()
        );
    }
}
