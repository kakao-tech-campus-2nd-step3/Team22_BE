package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.ErrorCode;

public enum FriendsAPIErrorCode implements ErrorCode {
    ALREADY_SEND_REQUEST("F001", "이미 보낸 요청입니다.");

    private final String code;
    private final String message;

    FriendsAPIErrorCode(String code, String message) {
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
