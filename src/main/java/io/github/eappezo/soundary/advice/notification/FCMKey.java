package io.github.eappezo.soundary.advice.notification;

import io.github.eappezo.soundary.core.identification.Identifier;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FCMKey implements Serializable {
    private String userId;
    private String fcmToken;

    public static FCMKey of(Identifier userId, String fcmToken) {
        return new FCMKey(userId.toString(), fcmToken);
    }
}
