package io.github.eappezo.soundary.core.notification;

public interface Notification {

    NotificationType type();

    String title();

    String body();

}
