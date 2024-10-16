package io.github.eappezo.soundary.services.authentication.infrastructure;

import io.github.eappezo.soundary.services.authentication.application.RefreshTokenDto;
import io.github.eappezo.soundary.services.authentication.application.RefreshTokenExtendStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class DefaultRefreshTokenExtendStrategy implements RefreshTokenExtendStrategy {
    @Value("${token.refresh-token-extend-threshold}")
    private double extendThreshold;
    private final JwtProperties jwtProperties;

    @Override
    public boolean hasToExtend(RefreshTokenDto refreshToken) {
        LocalDateTime now = LocalDateTime.now();
        double duration = refreshToken
                .refreshedAt()
                .until(now, ChronoUnit.SECONDS);
        double used = duration / jwtProperties.getRefreshTokenExpiration();

        return (1 - used) < extendThreshold;
    }
}
