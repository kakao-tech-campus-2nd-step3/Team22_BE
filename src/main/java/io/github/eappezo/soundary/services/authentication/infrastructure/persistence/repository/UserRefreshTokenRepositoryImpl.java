package io.github.eappezo.soundary.services.authentication.infrastructure.persistence.repository;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.authentication.application.RefreshTokenDto;
import io.github.eappezo.soundary.services.authentication.application.UserRefreshTokenRepository;
import io.github.eappezo.soundary.services.authentication.infrastructure.persistence.entity.UserRefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRefreshTokenRepositoryImpl implements UserRefreshTokenRepository {
    private final JpaUserRefreshTokenRepository jpaUserRefreshTokenRepository;

    @Override
    public void save(Identifier userId, RefreshTokenDto refreshToken) {
        jpaUserRefreshTokenRepository.save(UserRefreshTokenEntity.of(userId, refreshToken));
    }

    @Override
    public Optional<RefreshTokenDto> findRefreshToken(Identifier userId) {
        return jpaUserRefreshTokenRepository
                .findById(userId.toString())
                .map((entity) ->
                        new RefreshTokenDto(
                                entity.getRefreshToken(),
                                entity.getUpdatedAt()
                        )
                );
    }
}
