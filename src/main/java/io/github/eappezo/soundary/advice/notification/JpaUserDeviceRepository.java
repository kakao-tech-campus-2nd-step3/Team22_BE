package io.github.eappezo.soundary.advice.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaUserDeviceRepository extends JpaRepository<UserDevice, FCMKey> {

    List<UserDevice> findAllByUserId(String userId);

    void deleteAllByUserId(String fcmToken);

    void deleteAllByFcmToken(String fcmToken);

}
