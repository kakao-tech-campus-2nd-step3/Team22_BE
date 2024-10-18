package io.github.eappezo.soundary.advice.notification;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FCMKeyManager implements Serializable {
    private String userId;
    private String fcmToken;
}
