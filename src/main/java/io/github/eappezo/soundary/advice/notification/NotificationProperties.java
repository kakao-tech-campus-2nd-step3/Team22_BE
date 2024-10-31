package io.github.eappezo.soundary.advice.notification;

import io.github.eappezo.soundary.core.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "")
public class NotificationProperties {
    private Map<String, NotificationFormat> notification;

    public NotificationFormat getFormat(NotificationType type) {
        return notification.get(toDashCase(type));
    }

    public record NotificationFormat(
            String title,
            String bodyFormat
    ) {
    }

    private String toDashCase(NotificationType type) {
        return type.name().toLowerCase().replace("_", "-");
    }
}
