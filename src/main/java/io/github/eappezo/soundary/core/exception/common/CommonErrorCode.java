package io.github.eappezo.soundary.core.exception.common;

import io.github.eappezo.soundary.core.exception.ErrorCode;

public enum CommonErrorCode implements ErrorCode {
    UNKNOWN_ERROR("C000", "알 수 없는 오류가 발생했습니다."),
    USER_NOT_FOUND("C001", "존재하지 않는 사용자입니다."),
    INVALID_REQUEST_PAYLOAD("C002", "요청 페이로드 형식이 올바르지 않습니다."),
    INVALID_REQUEST_PARAMETER("C003", "요청 파라미터 형식이 올바르지 않습니다.");

    private final String code;
    private final String message;

    CommonErrorCode(String code, String message) {
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
