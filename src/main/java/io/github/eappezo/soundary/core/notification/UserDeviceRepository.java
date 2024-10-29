package io.github.eappezo.soundary.core.notification;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.List;

public interface UserDeviceRepository {

    boolean registerDevice(Identifier userId, String deviceToken);

    boolean removeDevice(Identifier userId, String deviceToken);

    List<String> getDevicesByUserId(String userId);

    void removeAllDevicesByUserId(String userId);

}
