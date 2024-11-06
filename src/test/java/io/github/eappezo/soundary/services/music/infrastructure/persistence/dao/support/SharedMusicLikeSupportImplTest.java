package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.domain.exception.AlreadyLikedSharedMusicException;
import io.github.eappezo.soundary.services.music.domain.exception.NotLikedSharedMusicException;
import io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.JpaSharedMusicLikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SharedMusicLikeSupportImplTest {
    Identifier userId = Identifier.fromString("user");
    Identifier sharedMusicId = Identifier.fromString("music");

    /*
    1. 이미 좋아요 했는데 좋아요 또 하는 경우
    2. 좋아요 안되어 있는데 좋아요 취소 하는 경우
     */
    @InjectMocks
    private SharedMusicLikeSupportImpl sharedMusicLikeSupport;
    @Mock
    private JpaSharedMusicLikeRepository jpaSharedMusicLikeRepository;

    @DisplayName("이미 좋아요 했는데 좋아요 또 하는 경우")
    @Test
    void likeAlreadyLikedMusic() {
        //given
        when(jpaSharedMusicLikeRepository.existsById(any())).thenReturn(true);

        //when,then
        assertThrows(AlreadyLikedSharedMusicException.class, () -> sharedMusicLikeSupport.like(userId, sharedMusicId));
    }

    @DisplayName("좋아요 안되어 있는데 좋아요 취소 하는 경우")
    @Test
    void unlikeNonLikedMusic() {
        //given
        when(jpaSharedMusicLikeRepository.existsById(any())).thenReturn(false);

        //when,then
        assertThrows(NotLikedSharedMusicException.class, () -> sharedMusicLikeSupport.unlike(userId, sharedMusicId));
    }
}