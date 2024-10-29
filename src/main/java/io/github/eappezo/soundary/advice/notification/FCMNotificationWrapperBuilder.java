package io.github.eappezo.soundary.advice.notification;

import io.github.eappezo.soundary.core.notification.Notification;
import io.github.eappezo.soundary.core.notification.NotificationBuilder;
import org.springframework.stereotype.Component;

@Component
public class FCMNotificationWrapperBuilder implements NotificationBuilder {
    @Override
    public Notification build(String title, String body) {
        return new FCMNotificationWrapper(title, body);
    }
}
