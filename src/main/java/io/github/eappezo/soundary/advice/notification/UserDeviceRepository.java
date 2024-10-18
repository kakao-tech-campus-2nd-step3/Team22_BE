package io.github.eappezo.soundary.advice.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDeviceRepository extends JpaRepository<UserDevice, FCMKeyManager> {
    List<UserDevice> findAllByUserId(String userId);
    void deleteAllByUserId(String fcmToken);
    void deleteAllByFcmToken(String fcmToken);
}
