package io.github.eappezo.soundary.services.authentication.api.controller;

import io.github.eappezo.soundary.services.authentication.api.dto.LoginRequest;
import io.github.eappezo.soundary.services.authentication.api.dto.LoginResponse;
import io.github.eappezo.soundary.services.authentication.api.dto.RefreshRequest;
import io.github.eappezo.soundary.services.authentication.api.dto.RefreshResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "인증/인가 API", description = "인증 및 인가를 처리합니다.")
public interface AuthAPI {

    LoginResponse login(@RequestBody LoginRequest request);

    RefreshResponse refresh(@RequestBody RefreshRequest request);

}
