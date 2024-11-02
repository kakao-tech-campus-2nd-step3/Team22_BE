package io.github.eappezo.soundary.core.notification;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.List;

public interface NotificationSender {

    void notice(
            NotificationType type,
            Identifier senderId,
            Identifier targetUserId
    );

    void notice(
            NotificationType type,
            Identifier senderId,
            List<Identifier> targetUserIds
    );

}
