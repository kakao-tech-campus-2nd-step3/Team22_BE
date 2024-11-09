package io.github.eappezo.soundary.services.user.application;

import io.github.eappezo.soundary.core.user.User;

public interface LeavedUserRepository {

    void save(User leavedUser);

}
