package io.github.eappezo.soundary.services.user.infrastructure.persistence.entity;

import io.github.eappezo.soundary.core.persistence.infrastructure.BaseAuditingEntity;
import io.github.eappezo.soundary.core.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LeavedUserEntity extends BaseAuditingEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "display_id", nullable = false, unique = true)
    private String displayId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    public static LeavedUserEntity fromDomain(User user) {
        return new LeavedUserEntity(
                user.getIdentifier().toString(),
                user.getDisplayId(),
                user.getNickname(),
                user.getDescription(),
                user.getProfileImageUrl()
        );
    }
}
