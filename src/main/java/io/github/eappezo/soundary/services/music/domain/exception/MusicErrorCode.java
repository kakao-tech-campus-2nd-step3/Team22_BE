package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MusicErrorCode implements ErrorCode {
    NOT_SUPPORTED_PLATFORM("M001", "지원하지 않는 플랫폼입니다."),
    TRACK_NOT_FOUND("M002", "곡을 찾을 수 없습니다."),
    TRACK_SEARCH_ERROR("M003", "곡 검색 중 오류가 발생했습니다."),
    NOT_FRIEND("M004", "친구가 아닌 사람에게는 음악을 공유할 수 없습니다.");

    private final String code;
    private final String message;

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
