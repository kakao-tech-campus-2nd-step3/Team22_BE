package io.github.eappezo.soundary.services.authentication.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.authentication.domain.SocialAccountRepository;
import io.github.eappezo.soundary.services.authentication.domain.SocialAccount;
import io.github.eappezo.soundary.services.authentication.domain.SocialPlatform;
import io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity.SocialAccountEntity;
import io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity.key.SocialAccountEntityKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SocialAccountRepositoryImpl implements SocialAccountRepository {
    private final JpaSocialAccountRepository jpaSocialAccountRepository;

    @Override
    public Optional<Identifier> findUserIdBy(SocialPlatform platform, String socialId) {
        return jpaSocialAccountRepository
                .findById(SocialAccountEntityKey.of(platform, socialId))
                .map(SocialAccountEntity::getUserId)
                .map(Identifier::fromString);
    }

    @Override
    public void save(SocialAccount account) {
        jpaSocialAccountRepository.save(SocialAccountEntity.from(account));
    }
}
