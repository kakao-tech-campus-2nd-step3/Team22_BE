package io.github.eappezo.soundary.advice.exception.handler;

import io.github.eappezo.soundary.core.exception.APIException;
import io.github.eappezo.soundary.core.exception.ErrorResponse;
import io.github.eappezo.soundary.core.exception.common.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        return ErrorResponse.of(exception.errorCode());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeException(RuntimeException exception) {
        log.error(stackTraceOf(exception));
        return ErrorResponse.of(CommonErrorCode.UNKNOWN_ERROR);
    }
}
