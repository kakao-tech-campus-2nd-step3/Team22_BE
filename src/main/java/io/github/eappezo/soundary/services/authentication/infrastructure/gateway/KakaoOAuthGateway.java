package io.github.eappezo.soundary.services.authentication.infrastructure.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.eappezo.soundary.services.authentication.application.OAuthGateway;
import io.github.eappezo.soundary.services.authentication.application.OAuthResult;
import io.github.eappezo.soundary.services.authentication.domain.SocialPlatform;
import io.github.eappezo.soundary.services.authentication.domain.exception.AuthenticationFailedException;
import io.github.eappezo.soundary.services.authentication.infrastructure.OAuthClientConfig;
import io.github.eappezo.soundary.services.authentication.infrastructure.OAuthClientProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static io.github.eappezo.soundary.services.authentication.infrastructure.DecodeInputStreamUtil.decodeInputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoOAuthGateway implements OAuthGateway {
    private final OAuthClientProperties clientProperties;
    private final RestClient restClient;

    @Override
    public OAuthResult authenticate(String token) {
        KakaoResourceResponse resource = getResource(token);
        return new OAuthResult(SocialPlatform.KAKAO, resource.id());
    }

    @Override
    public SocialPlatform getPlatform() {
        return SocialPlatform.KAKAO;
    }

    private KakaoResourceResponse getResource(String token) {
        OAuthClientConfig config = clientProperties.getClientConfig(SocialPlatform.KAKAO);
        return restClient
                .get()
                .uri(config.resourceUri())
                .headers(headers -> {
                    headers.setBearerAuth(token);
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                })
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error(
                            "Failed to get resource from Kakao API. body: \n{}",
                            decodeInputStream(response.getBody())
                    );
                    throw new AuthenticationFailedException();
                })
                .toEntity(KakaoResourceResponse.class)
                .getBody();
    }

    private record KakaoResourceResponse(@JsonProperty("id") String id) {
    }
}
