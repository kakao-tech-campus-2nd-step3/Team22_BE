package io.github.eappezo.soundary.services.authentication.domain;

import io.github.eappezo.soundary.core.identification.Identifier;

public record SocialAccount(
        SocialPlatform platform,
        String socialId,
        Identifier userId
) {
}
