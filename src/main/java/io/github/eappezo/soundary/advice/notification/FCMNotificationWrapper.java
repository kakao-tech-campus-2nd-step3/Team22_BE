package io.github.eappezo.soundary.advice.notification;

import com.google.firebase.messaging.Message;
import io.github.eappezo.soundary.core.notification.Notification;

public class FCMNotificationWrapper implements Notification {
    private final Message message;

    public FCMNotificationWrapper(Message message) {
        this.message = message;
    }

    public Message message() {
        return message;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getBody() {
        return "";
    }

    @Override
    public String getTargetToken() {
        return "";
    }
}
