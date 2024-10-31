package io.github.eappezo.soundary.core.notification;

public interface NotificationBuilder {

    Notification build(
            NotificationType type,
            String title,
            String body
    );

}
