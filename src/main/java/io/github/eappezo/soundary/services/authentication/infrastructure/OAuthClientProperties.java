package io.github.eappezo.soundary.services.authentication.infrastructure;

import io.github.eappezo.soundary.services.authentication.domain.SocialPlatform;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Optional;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "oauth2")
public class OAuthClientProperties {
    private Map<String, OAuthClientConfig> clients;

    public OAuthClientConfig getClientConfig(SocialPlatform platform) {
        return Optional
                .ofNullable(clients.get(platform.name().toLowerCase()))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported platform: " + platform));
    }
}

