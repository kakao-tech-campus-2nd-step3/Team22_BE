package io.github.eappezo.soundary.core.exception;

public class APIException extends RuntimeException {
    private final ErrorCode errorCode;

    private static String buildErrorMessage(ErrorCode errorCode, String message) {
        return String.format("%s:\n %s", errorCode.code(), message);
    }

    public APIException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public APIException(ErrorCode errorCode, String message) {
        super(buildErrorMessage(errorCode, message));
        this.errorCode = errorCode;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
