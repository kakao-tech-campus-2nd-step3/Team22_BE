package io.github.eappezo.soundary.advice.notification;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import io.github.eappezo.soundary.core.notification.Notification;

public record FCMNotificationWrapper(
        String title,
        String body
) implements Notification {
    public Message buildFCMMessage(String deviceToken) throws FirebaseMessagingException {
        return Message.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(title)
                        .build())
                .setToken(deviceToken)  // 대상 디바이스의 등록 토큰
                .build();
    }
}
