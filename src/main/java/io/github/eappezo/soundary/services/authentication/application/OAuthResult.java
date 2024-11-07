package io.github.eappezo.soundary.services.authentication.application;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.authentication.domain.SocialAccount;
import io.github.eappezo.soundary.services.authentication.domain.SocialPlatform;

public record OAuthResult(
        SocialPlatform platform,
        String socialId
) {
    public SocialAccount makeSocialAccountOf(Identifier userId) {
        return new SocialAccount(platform, socialId, userId);
    }
}
