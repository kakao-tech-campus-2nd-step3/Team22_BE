package io.github.eappezo.soundary.services.user.domain.exception;

import io.github.eappezo.soundary.core.exception.ErrorCode;

public enum UserErrorCode implements ErrorCode {
    LABEL_INVALID_VALUE("U001", "전달된 label 값이 정의된 값이 아님"),
    ALREADY_INITIALIZED_USER("U002", "이미 초기화된 사용자 정보가 존재함"),;

    private final String code;
    private final String message;

    UserErrorCode(String code, String message) {
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
