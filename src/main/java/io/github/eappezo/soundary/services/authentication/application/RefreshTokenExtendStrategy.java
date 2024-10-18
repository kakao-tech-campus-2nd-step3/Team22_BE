package io.github.eappezo.soundary.services.authentication.application;

public interface RefreshTokenExtendStrategy {

    boolean hasToExtend(RefreshTokenDto refreshToken);

}
