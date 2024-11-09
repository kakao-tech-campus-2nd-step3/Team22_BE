package io.github.eappezo.soundary.services.music.application.share.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.github.eappezo.soundary.core.exception.common.InvalidRequestPayloadException;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicLikeSupport;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicQueryCondition;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicRetrieveSupport;
import io.github.eappezo.soundary.services.music.domain.SharedMusicRepository;
import io.github.eappezo.soundary.services.music.domain.exception.NotExistsSharedMusicException;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SharedMusicServiceTest {
    @InjectMocks
    private SharedMusicService sharedMusicService;

    @Mock
    private SharedMusicRepository sharedMusicRepository;
    @Mock
    private SharedMusicRetrieveSupport sharedMusicRetrieveSupport;
    @Mock
    private SharedMusicLikeSupport sharedMusicLikeSupport;

    private Identifier userId = Identifier.fromString("user");
    private SharedMusicQueryCondition sharedMusicQueryCondition;
    private Identifier sharedMusicId = Identifier.fromString("music");

    /*
    1. 음악이 없는 경우
    2. 내가 공유 안한 음악의 좋아요한 사람을 확인하는 경우.(반영 안됨)
    3. 나한테 공유 안된 음악에 좋아요를 하면 안된다.
    4. 공유 받은 음악이 아닌 곳에 좋아요 취소를 하는 경우
     */

    /*
    1. sharedMusicQueryCondition page가 음수로 오는 경우
    2. sharedMusicQueryCondition size가 1보다 작은 수로 오는 경우
    3. sharedMusicQueryCondition 시작 날짜가 끝나는 날짜 보다 뒤에 있는 경우
     */
    @DisplayName("Shared_Music_Query_Condition_Test")
    @Test
    void sharedMusicQueryConditionTest(){
        //given
        int page = 1;
        int size = 1;
        boolean onlyExposured = true;
        LocalDateTime startDate = LocalDateTime.of(2023, Month.APRIL, 1, 1, 1);
        LocalDateTime endDate = LocalDateTime.of(2024, Month.APRIL, 1, 1, 1);

        //when, then
        assertAll(
            () -> assertThrows(InvalidRequestPayloadException.class,
                () -> new SharedMusicQueryCondition(
                    -1, size, onlyExposured, startDate, endDate
                )
            ),
            () -> assertThrows(InvalidRequestPayloadException.class,
                () -> new SharedMusicQueryCondition(
                    page, -1, onlyExposured, startDate, endDate
                )
            ),
            () -> assertThrows(InvalidRequestPayloadException.class,
                () -> new SharedMusicQueryCondition(
                    page, size, onlyExposured, endDate.plusDays(1), endDate
                )
            )
        );
    }

    @DisplayName("음악이 없는 경우")
    @Test
    void getSentSharedMusic() {
        //given
        when(sharedMusicRepository.notExists(any())).thenReturn(true);

        //when,then
        assertThrows(NotExistsSharedMusicException.class, () -> sharedMusicService.getSharedMusicLikes(sharedMusicId));
    }

    @DisplayName("나한테 공유 안된 음악에 좋아요를 하면 안된다.")
    @Test
    void notSharedMusicLike() {
        //given
        when(sharedMusicRepository.isSharedToUser(sharedMusicId, userId)).thenReturn(false);

        //when,then
        assertThrows(NotExistsSharedMusicException.class, () -> sharedMusicService.likeMusic(userId, sharedMusicId));
    }

    @DisplayName("공유 받은 음악이 아닌 곳에 좋아요 취소를 하는 경우")
    @Test
    void unlikeMusic() {
        //given
        when(sharedMusicRepository.isSharedToUser(sharedMusicId, userId)).thenReturn(false);

        //when,then
        assertThrows(NotExistsSharedMusicException.class, () -> sharedMusicService.unlikeMusic(userId, sharedMusicId));

    }
}