package io.github.eappezo.soundary.services.authentication.application;

import io.github.eappezo.soundary.services.authentication.domain.SocialPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuthGatewayRegistryImpl implements OAuthGatewayRegistry {
    private final List<OAuthGateway> oAuthGateways;

    @Override
    public OAuthGateway getOAuthGateway(SocialPlatform platform) {
        return oAuthGateways.stream()
                .filter(gateway -> gateway.getPlatform() == platform)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not supported platform"));
    }
}
