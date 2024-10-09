package io.github.eappezo.soundary.services.music.domain.exception;

import io.github.eappezo.soundary.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MusicErrorCode implements ErrorCode {
    NOT_SUPPORTED_PLATFORM("M001", "지원하지 않는 플랫폼입니다."),
    TRACK_NOT_FOUND("M002", "곡을 찾을 수 없습니다."),
    TRACK_SEARCH_ERROR("M003", "곡 검색 중 오류가 발생했습니다."),
    NOT_FRIEND("M004", "친구가 아닌 사람에게는 음악을 공유할 수 없습니다."),
    NOT_EXISTS_SHARED_MUSIC("M005", "공유된 음악이 존재하지 않습니다."),
    ALREADY_LIKED_SHARED_MUSIC("M006", "이미 좋아요를 누른 음악입니다."),
    NOT_LIKED_SHARED_MUSIC("M007", "좋아요를 누르지 않은 음악입니다.");

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
