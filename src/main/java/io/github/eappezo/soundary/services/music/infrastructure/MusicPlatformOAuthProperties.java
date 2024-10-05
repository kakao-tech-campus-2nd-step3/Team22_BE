package io.github.eappezo.soundary.services.music.infrastructure;

import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "app")
public class MusicPlatformOAuthProperties {
    private Map<String, MusicPlatformOAuthConfig> musicPlatforms;

    public MusicPlatformOAuthConfig getConfig(MusicPlatform platform) {
        return musicPlatforms.get(platform.name().toLowerCase());
    }
}
