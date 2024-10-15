package io.github.eappezo.soundary.core.notification;

public interface Notification {
    String getTitle();
    String getBody();
    String getTargetToken();
}
