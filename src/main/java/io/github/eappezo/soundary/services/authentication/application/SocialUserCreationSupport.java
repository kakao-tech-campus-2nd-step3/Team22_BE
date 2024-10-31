package io.github.eappezo.soundary.services.authentication.application;

import io.github.eappezo.soundary.core.user.User;

public interface SocialUserCreationSupport {

    User registerNewSocialUserBy(OAuthResult oAuthResult);

}
