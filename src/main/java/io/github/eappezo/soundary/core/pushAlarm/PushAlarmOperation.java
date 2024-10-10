package io.github.eappezo.soundary.core.pushAlarm;

import io.github.eappezo.soundary.core.identification.Identifier;

public interface PushAlarmOperation<T> {
    T saveToken(Identifier userId, String token);
    T getToken(Identifier userId);
    T deleteToken(Identifier userId);
}
