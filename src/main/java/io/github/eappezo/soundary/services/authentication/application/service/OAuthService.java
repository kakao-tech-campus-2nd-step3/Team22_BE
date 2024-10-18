package io.github.eappezo.soundary.services.authentication.application.service;

import io.github.eappezo.soundary.core.exception.common.UserNotFoundException;
import io.github.eappezo.soundary.core.persistence.PersistenceOperationGateway;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.core.user.UserRepository;
import io.github.eappezo.soundary.services.authentication.application.*;
import io.github.eappezo.soundary.services.authentication.domain.SocialAccountRepository;
import io.github.eappezo.soundary.services.authentication.domain.SocialUserCreationSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final TokenProvider tokenProvider;
    private final OAuthGatewayRegistry oAuthGatewayRegistry;
    private final SocialUserCreationSupport socialUserCreationSupport;
    private final SocialAccountRepository socialAccountRepository;
    private final UserRepository userRepository;
    private final PersistenceOperationGateway persistenceOperationGateway;

    public LoginResultDto login(OAuthRequestDto request) {
        OAuthGateway oauthGateway = oAuthGatewayRegistry.getOAuthGateway(request.platform());
        OAuthResult result = oauthGateway.authenticate(request.token());
        User user = persistenceOperationGateway.executeOperation(() ->
                getSocialUserOrCreateBy(result)
        );
        return createAuthentication(user);
    }

    private User getSocialUserOrCreateBy(OAuthResult oAuthResult) {
        return socialAccountRepository
                .findUserIdBy(oAuthResult.platform(), oAuthResult.socialId())
                .map((it) -> userRepository.findById(it).orElseThrow(UserNotFoundException::new))
                .orElseGet(() -> socialUserCreationSupport.registerNewSocialUserBy(oAuthResult));
    }

    private LoginResultDto createAuthentication(User user) {
        String accessToken = tokenProvider.generateAccessTokenFrom(user);
        String refreshToken = tokenProvider.generateRefreshTokenFrom(user);
        Long expirationTime = tokenProvider.getAccessTokenExpirationTime();

        return new LoginResultDto(accessToken, refreshToken, expirationTime);
    }
}
