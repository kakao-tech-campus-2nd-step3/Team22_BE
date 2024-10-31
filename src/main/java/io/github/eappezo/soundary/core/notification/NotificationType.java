package io.github.eappezo.soundary.core.notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType {
    MUSIC_SHARED("N0001"),
    LIKE_RECEIVED("N0002"),
    RECEIVED_FRIEND_REQUEST("N0003");

    private final String code;

    public String code() {
        return code;
    }
}
