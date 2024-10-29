package io.github.eappezo.soundary.advice.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.Notification;
import io.github.eappezo.soundary.core.notification.NotificationGateway;
import io.github.eappezo.soundary.core.notification.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.eappezo.soundary.core.ExceptionUtil.stackTraceOf;

@Service
@Slf4j
@RequiredArgsConstructor
public class FCMNotificationProducer implements NotificationGateway {
    private final UserDeviceRepository userDeviceRepository;

    public void notice(
            Identifier userId,
            Notification notification
    ) {
        List<String> devices = userDeviceRepository.getDevicesByUserId(userId);
        for (String deviceToken : devices) {
            try {
                Message message = ((FCMNotificationWrapper) notification).buildFCMMessage(deviceToken);
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException exception) {
                log.error("Failed to build FCM. device : {} \n {}",
                        deviceToken,
                        stackTraceOf(exception)
                );
            }
        }
    }
}
