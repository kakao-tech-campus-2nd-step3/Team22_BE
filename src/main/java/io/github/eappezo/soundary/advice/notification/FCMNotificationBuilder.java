package io.github.eappezo.soundary.advice.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eappezo.soundary.advice.notification.NotificationProperties.NotificationFormat;
import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.Notification;
import io.github.eappezo.soundary.core.notification.NotificationType;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FCMNotificationBuilder {
    private final UserRepository userRepository;
    private final NotificationProperties notificationProperties;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public Notification build(
            NotificationType type,
            Identifier senderId
    ) {
        User user = userRepository
                .findById(senderId)
                .orElseThrow(UserNotFoundException::new);
        NotificationFormat format = notificationProperties.getFormat(type);
        String title = format.title();
        String message = format
                .bodyFormat()
                .replace("{sender}", buildUserInfo(user));
        try {
            String body = objectMapper.writeValueAsString(FCMBody.of(type.code(), message));
            return new FCMNotificationWrapper(
                    type,
                    title,
                    body
            );
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private record FCMBody(
            String code,
            String message
    ) {
        public static FCMBody of(String code, String message) {
            return new FCMBody(code, message);
        }
    }

    private String buildUserInfo(User user) {
        return String.format("%s(@%s)", user.getNickname(), user.getDisplayId());
    }
}
