package io.github.eappezo.soundary.core.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    private ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.code();
        this.message = errorCode.message();
    }
}
