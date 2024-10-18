package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.ErrorCode;

public enum FriendErrorCode implements ErrorCode {
    ALREADY_SENT_REQUEST("F001", "이미 보낸 요청입니다."),
    FRIEND_LIMIT("F002", "맺을 수 있는 친구 수가 제한되어있습니다."),;

    private final String code;
    private final String message;

    FriendErrorCode(String code, String message) {
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
