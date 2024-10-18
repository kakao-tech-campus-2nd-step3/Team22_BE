package io.github.eappezo.soundary.services.user.domain.exception;

import io.github.eappezo.soundary.core.exception.ErrorCode;

public enum LabelErrorCode implements ErrorCode {

    LABEL_INVALID_VALUE("L001", "전달된 label 값이 정의된 값이 아님");

    private final String code;
    private final String message;

    LabelErrorCode(String code, String message) {
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
