package io.github.eappezo.soundary.services.authentication.domain.exception;

import io.github.eappezo.soundary.core.exception.ErrorCode;

public enum AuthenticationErrorCode implements ErrorCode {
    AUTHENTICATION_FAILED("A001", "인증에 실패했습니다."),
    AUTHENTICATION_EXPIRED("A002", "인증이 만료되었습니다.");

    private final String code;
    private final String message;

    AuthenticationErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
