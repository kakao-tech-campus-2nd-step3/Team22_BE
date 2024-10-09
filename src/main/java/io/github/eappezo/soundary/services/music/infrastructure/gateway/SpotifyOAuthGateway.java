package io.github.eappezo.soundary.services.music.infrastructure.gateway;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.eappezo.soundary.services.authentication.domain.exception.AuthenticationFailedException;
import io.github.eappezo.soundary.services.music.application.PlatformAccessToken;
import io.github.eappezo.soundary.services.music.application.authentication.MusicPlatformOAuthGateway;
import io.github.eappezo.soundary.services.music.domain.MusicPlatform;
import io.github.eappezo.soundary.services.music.infrastructure.MusicPlatformOAuthConfig;
import io.github.eappezo.soundary.services.music.infrastructure.MusicPlatformOAuthProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;

import static io.github.eappezo.soundary.core.DecodeInputStreamUtil.decodeInputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class SpotifyOAuthGateway implements MusicPlatformOAuthGateway {
    private final MusicPlatformOAuthProperties properties;
    private final RestClient restClient;
    private MusicPlatformOAuthConfig config;

    @PostConstruct
    public void init() {
        config = properties.getConfig(getPlatform());
    }

    @Override
    public PlatformAccessToken getAccessToken() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        SpotifyOAuthTokenResponse tokenResponse = restClient
                .post()
                .uri(config.tokenUri())
                .headers(
                        headers -> {
                            headers.add("Authorization", buildBasicAuthHeader());
                            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                        }
                )
                .body(params)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        (request, response) -> {
                            log.error(
                                    "Failed to get token from Spotify API. body: \n{}",
                                    decodeInputStream(response.getBody())
                            );
                            throw new AuthenticationFailedException();
                        }
                )
                .toEntity(SpotifyOAuthTokenResponse.class)
                .getBody();
        assert tokenResponse != null;
        return PlatformAccessToken.of(
                getPlatform(),
                tokenResponse.accessToken(),
                tokenResponse.expiresIn()
        );
    }

    @Override
    public MusicPlatform getPlatform() {
        return MusicPlatform.SPOTIFY;
    }

    private String buildBasicAuthHeader() {
        return "Basic " + Base64.getEncoder().encodeToString(
                (String.format("%s:%s", config.clientId(), config.clientSecret())).getBytes()
        );
    }

    @JsonNaming(SnakeCaseStrategy.class)
    private record SpotifyOAuthTokenResponse(
            String accessToken,
            String tokenType,
            Long expiresIn
    ) {
    }
}
