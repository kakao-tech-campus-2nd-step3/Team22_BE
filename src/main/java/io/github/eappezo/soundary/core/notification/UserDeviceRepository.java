package io.github.eappezo.soundary.core.notification;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.List;

public interface UserDeviceRepository {

    void registerDevice(Identifier userId, String deviceToken);

    boolean removeDevice(Identifier userId, String deviceToken);

    List<String> getDevicesByUserId(Identifier userId);

    List<String> getDevicesByUserIds(List<Identifier> userIds);

    void removeAllDevicesByUserId(Identifier userId);

}
