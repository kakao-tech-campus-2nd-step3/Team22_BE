package io.github.eappezo.soundary.services.friend.domain.exception;

import io.github.eappezo.soundary.core.exception.ErrorCode;

public enum FriendErrorCode implements ErrorCode {
    CANNOT_SENT_REQUEST("F001", "해당 유저에게 요청을 보낼 수 없습니다."),
    FRIEND_LIMIT("F002", "맺을 수 있는 친구 수가 제한되어있습니다."),
    CANNOT_REQUEST_TO_MYSELF("F003", "자기 자신에게 친구 요청을 보낼 수 없습니다."),;

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
