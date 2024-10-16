package io.github.eappezo.soundary.core.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN(3),
    USER(2),
    PENDING(1),
    LEAVED(4);

    private final int priority;

    UserRole(int priority) {
        this.priority = priority;
    }
}
