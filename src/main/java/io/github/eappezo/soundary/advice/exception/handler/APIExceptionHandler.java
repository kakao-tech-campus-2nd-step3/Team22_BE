package io.github.eappezo.soundary.advice.exception.handler;

import io.github.eappezo.soundary.core.exception.APIException;
import io.github.eappezo.soundary.core.exception.ErrorResponse;
import io.github.eappezo.soundary.core.exception.common.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static io.github.eappezo.soundary.core.ExceptionUtil.stackTraceOf;

@Slf4j
@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(APIException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAPIException(APIException exception) {
        log.error("request failed: {} ({})",
                exception.errorCode().message(),
                exception.errorCode().code()
        );
        return ErrorResponse.of(exception.errorCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ErrorResponse.of(CommonErrorCode.INVALID_REQUEST_PAYLOAD);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeException(RuntimeException exception) {
        log.error(stackTraceOf(exception));
        return ErrorResponse.of(CommonErrorCode.UNKNOWN_ERROR);
    }
}
