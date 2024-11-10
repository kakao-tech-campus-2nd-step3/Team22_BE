package io.github.eappezo.soundary.core.exception.common;

import io.github.eappezo.soundary.core.exception.ErrorCode;

public enum CommonErrorCode implements ErrorCode {
    UNKNOWN_ERROR("C000", "알 수 없는 오류가 발생했습니다."),
    USER_NOT_FOUND("C001", "존재하지 않는 사용자입니다."),
    INVALID_REQUEST_PAYLOAD("C002", "요청 페이로드 형식이 올바르지 않습니다."),
    INVALID_REQUEST_PARAMETER("C003", "요청 파라미터 형식이 올바르지 않습니다."),
    NOT_AUTHORIZED("C004", "인가되지 않은 행동입니다."),
    ALREADY_EXISTS_USER("C005", "이미 존재하는 사용자입니다."),
    CANNOT_UPLOAD_IMAGE("C006", "이미지를 업로드할 수 없습니다."),
    RESOURCE_NOT_EXISTS("C007", "존재하지 않는 리소스입니다.");

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
