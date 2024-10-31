package io.github.eappezo.soundary.advice.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.Notification;
import io.github.eappezo.soundary.core.notification.NotificationGateway;
import io.github.eappezo.soundary.core.notification.NotificationType;
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
    private final ObjectMapper objectMapper;

    public void notice(
            Identifier userId,
            Notification notification
    ) {
        List<String> devices = userDeviceRepository.getDevicesByUserId(userId);
        for (String deviceToken : devices) {
            try {
                String title = notification.title();
                FCMBody fcmBody = FCMBody.of(notification.type(), notification.body());

                Message message = buildFCMMessage(
                        title,
                        objectMapper.writeValueAsString(fcmBody),
                        deviceToken
                );
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException exception) {
                log.error("Failed to build FCM. device : {} \n {}",
                        deviceToken,
                        stackTraceOf(exception)
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Message buildFCMMessage(
            String title,
            String body,
            String deviceToken
    ) {
        return Message.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setToken(deviceToken)  // 대상 디바이스의 등록 토큰
                .build();
    }

    private record FCMBody(
            String code,
            String title
    ) {
        public static FCMBody of(NotificationType type, String title) {
            return new FCMBody(type.code(), title);
        }
    }
}
