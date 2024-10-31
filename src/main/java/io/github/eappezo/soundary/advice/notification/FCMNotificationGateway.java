package io.github.eappezo.soundary.advice.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.Notification;
import io.github.eappezo.soundary.core.notification.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.eappezo.soundary.core.ExceptionUtil.stackTraceOf;

@Slf4j
@Service
public class FCMNotificationGateway {
    public void notice(
            List<String> devices,
            Notification notification
    ) {
        for (String deviceToken : devices) {
            try {
                Message message = buildFCMMessage(
                        notification.title(),
                        notification.body(),
                        deviceToken
                );
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException exception) {
                log.error("Failed to build FCM. device : {} \n {}",
                        deviceToken,
                        stackTraceOf(exception)
                );
            }
        }
    }

    private Message buildFCMMessage(
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
}
