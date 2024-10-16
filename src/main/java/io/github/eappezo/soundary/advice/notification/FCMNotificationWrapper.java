package io.github.eappezo.soundary.advice.notification;

import com.google.firebase.messaging.Message;
import io.github.eappezo.soundary.core.notification.Notification;

import com.google.firebase.messaging.Message;
import io.github.eappezo.soundary.core.notification.Notification;

public class FCMNotificationWrapper implements Notification {
    private final Message message;
    private final String title;
    private final String body;
    private final String targetToken;

    public FCMNotificationWrapper(Message message, String title, String body, String targetToken) {
        this.message = message;
        this.title = title;
        this.body = body;
        this.targetToken = targetToken;
    }

    public Message message() {
        return message;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String getTargetToken() {
        return targetToken;
    }
}
