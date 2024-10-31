package io.github.eappezo.soundary.advice.notification;

import io.github.eappezo.soundary.core.async.AsyncAdvice;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.Notification;
import io.github.eappezo.soundary.core.notification.NotificationSender;
import io.github.eappezo.soundary.core.notification.NotificationType;
import io.github.eappezo.soundary.core.notification.UserDeviceRepository;
import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FCMNotificationSender implements NotificationSender {
    private final PersistenceOperationGateway persistenceOperationGateway;

    private final UserDeviceRepository userDeviceRepository;
    private final FCMNotificationBuilder fcmNotificationBuilder;
    private final FCMNotificationGateway fcmNotificationGateway;

    @Override
    public void notice(
            NotificationType type,
            Identifier senderId,
            Identifier targetUserId
    ) {
        NotificationWithTarget targetedNotification = persistenceOperationGateway.executeReadOnlyOperation(() -> {
            List<String> devices = userDeviceRepository.getDevicesByUserId(targetUserId);
            Notification notification = fcmNotificationBuilder.build(type, senderId);

            return new NotificationWithTarget(notification, devices);
        });
        fcmNotificationGateway.notice(
                targetedNotification.targetDevices(),
                targetedNotification.notification()
        );
    }

    @Override
    public void notice(
            NotificationType type,
            Identifier senderId,
            List<Identifier> targetUserIds
    ) {
        NotificationWithTarget targetedNotification = persistenceOperationGateway.executeReadOnlyOperation(() -> {
            List<String> devices = userDeviceRepository.getDevicesByUserIds(targetUserIds);
            Notification notification = fcmNotificationBuilder.build(type, senderId);

            return new NotificationWithTarget(notification, devices);
        });
        fcmNotificationGateway.notice(
                targetedNotification.targetDevices(),
                targetedNotification.notification()
        );
    }

    private record NotificationWithTarget(
            Notification notification,
            List<String> targetDevices
    ) {
    }
}
