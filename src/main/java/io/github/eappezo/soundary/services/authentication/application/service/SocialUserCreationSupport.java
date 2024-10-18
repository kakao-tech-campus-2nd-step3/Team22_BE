package io.github.eappezo.soundary.services.authentication.application.service;

import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.services.authentication.application.OAuthResult;

public interface SocialUserCreationSupport {

    User registerNewSocialUserBy(OAuthResult oAuthResult);

}
