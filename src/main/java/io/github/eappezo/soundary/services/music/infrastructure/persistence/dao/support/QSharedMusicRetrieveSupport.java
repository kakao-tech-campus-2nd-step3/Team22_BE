package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.ReceivedSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SentSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicRetrieveSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.github.eappezo.soundary.core.persistence.infrastructure.QUserEntity.userEntity;
import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QSharedMusicEntity.sharedMusicEntity;
import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QSharedMusicTargetEntity.sharedMusicTargetEntity;
import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QTrackEntity.trackEntity;

@Component
@RequiredArgsConstructor
public class QSharedMusicRetrieveSupport implements SharedMusicRetrieveSupport {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SentSharedMusicDto> getSentSharedMusic(Identifier userId) {
        return jpaQueryFactory
                .select(
                        new QSentSharedMusicProjection(
                                sharedMusicEntity.id,
                                sharedMusicEntity.trackId,
                                trackEntity.title,
                                trackEntity.artists,
                                trackEntity.albumTitle,
                                trackEntity.albumCoverUrl,
                                trackEntity.previewMp3Url,
                                trackEntity.durationInSeconds,
                                sharedMusicEntity.comment,
                                sharedMusicEntity.createdAt
                        )
                )
                .from(sharedMusicEntity)
                .join(trackEntity)
                .on(sharedMusicEntity.trackId.eq(trackEntity.id))
                .where(
                        sharedMusicEntity.fromUserId.eq(userId.toString())
                )
                .fetch()
                .stream()
                .map(SentSharedMusicProjection::toDto)
                .toList();
    }

    @Override
    public List<ReceivedSharedMusicDto> getReceivedSharedMusic(Identifier userId) {
        return jpaQueryFactory
                .select(
                        new QReceivedSharedMusicProjection(
                                sharedMusicEntity.id,
                                sharedMusicEntity.fromUserId,
                                userEntity.displayId,
                                userEntity.nickname,
                                userEntity.profileImageUrl,
                                sharedMusicEntity.trackId,
                                trackEntity.title,
                                trackEntity.artists,
                                trackEntity.albumTitle,
                                trackEntity.albumCoverUrl,
                                trackEntity.previewMp3Url,
                                trackEntity.durationInSeconds,
                                sharedMusicEntity.comment,
                                sharedMusicEntity.createdAt
                        )
                )
                .from(sharedMusicTargetEntity)
                .join(sharedMusicEntity)
                .on(sharedMusicTargetEntity.sharedMusicId.eq(sharedMusicEntity.id))
                .join(trackEntity)
                .on(sharedMusicEntity.trackId.eq(trackEntity.id))
                .join(userEntity)
                .on(sharedMusicEntity.fromUserId.eq(userEntity.id))
                .where(
                        sharedMusicTargetEntity.targetUserId.eq(userId.toString())
                )
                .fetch()
                .stream()
                .map(ReceivedSharedMusicProjection::toDto)
                .toList();
    }
}
