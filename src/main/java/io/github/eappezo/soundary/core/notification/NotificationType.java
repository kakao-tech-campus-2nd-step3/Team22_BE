package io.github.eappezo.soundary.core.notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType {
    MUSIC_SHARED("N0001", false),
    RECEIVED_FRIEND_REQUEST("N0002", true),
    ACCEPTED_FRIEND_REQUEST("N0003", true),;

    private final String code;
    private final Boolean isForeground;

    public String code() {
        return code;
    }

    public Boolean isBackground() {
        return !isForeground;
    }
}
