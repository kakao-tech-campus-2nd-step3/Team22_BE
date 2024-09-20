package io.github.eappezo.soundary.services.authentication.api.dto;

import io.github.eappezo.soundary.services.authentication.application.OAuthRequestDto;
import io.github.eappezo.soundary.services.authentication.domain.SocialPlatform;

public record LoginRequest(
        SocialPlatform platform,
        String token
) {
    public OAuthRequestDto to(){
        return new OAuthRequestDto(platform, token);
    }
}
