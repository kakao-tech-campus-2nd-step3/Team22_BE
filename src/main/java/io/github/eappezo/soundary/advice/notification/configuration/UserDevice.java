package io.github.eappezo.soundary.advice.notification.configuration;

import io.github.eappezo.soundary.advice.notification.FCMKeyManager;
import io.github.eappezo.soundary.core.persistence.infrastructure.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@IdClass(FCMKeyManager.class)
@Entity(name = "userDevice")
public class UserDevice extends BaseEntity {
    @Id
    @Column(name = "userId")
    private String userId;

    @Column(name = "fcmToken")
    private String fcmToken;

}
