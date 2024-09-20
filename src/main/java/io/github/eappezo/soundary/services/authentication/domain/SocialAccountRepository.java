package io.github.eappezo.soundary.services.authentication.domain;

import io.github.eappezo.soundary.core.identification.Identifier;

import java.util.Optional;

public interface SocialAccountRepository {

    Optional<Identifier> findUserIdBy(SocialPlatform platform, String socialId);

    void save(SocialAccount account);

}
