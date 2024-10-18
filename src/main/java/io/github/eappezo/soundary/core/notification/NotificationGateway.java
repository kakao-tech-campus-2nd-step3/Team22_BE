package io.github.eappezo.soundary.core.notification;

public interface NotificationGateway {

    void notice(Notification notification);
    void registerDevice(String userId, String fcmToken);
}
