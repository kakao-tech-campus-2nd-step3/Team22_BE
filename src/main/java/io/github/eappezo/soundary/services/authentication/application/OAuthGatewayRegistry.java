package io.github.eappezo.soundary.services.authentication.application;

import io.github.eappezo.soundary.services.authentication.domain.SocialPlatform;

public interface OAuthGatewayRegistry {

    OAuthGateway getOAuthGateway(SocialPlatform platform);

}
