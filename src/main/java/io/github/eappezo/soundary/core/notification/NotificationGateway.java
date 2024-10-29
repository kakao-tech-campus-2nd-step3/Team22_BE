package io.github.eappezo.soundary.core.notification;

import io.github.eappezo.soundary.core.identification.Identifier;

public interface NotificationGateway {

    void notice(Identifier userId, Notification notification);

}
