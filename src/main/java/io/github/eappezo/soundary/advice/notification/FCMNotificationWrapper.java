package io.github.eappezo.soundary.advice.notification;

import io.github.eappezo.soundary.core.notification.Notification;
import io.github.eappezo.soundary.core.notification.NotificationType;

public record FCMNotificationWrapper(
        NotificationType type,
        String title,
        String body
) implements Notification {
}
