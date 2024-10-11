package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.eappezo.soundary.core.Page;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.SentSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.ReceivedSharedMusicDto;
import io.github.eappezo.soundary.services.music.application.share.SentSharedMusicQueryCondition;
import io.github.eappezo.soundary.services.music.application.share.SharedMusicRetrieveSupport;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    public Page<SentSharedMusicDto> getSentSharedMusic(
            Identifier userId,
            SentSharedMusicQueryCondition condition
    ) {
        long offset = condition.offset();
        String rawUserId = userId.toString();
        Long total = jpaQueryFactory
                .select(sharedMusicEntity.id.count())
                .from(sharedMusicEntity)
                .where(
                        sharedMusicEntity.fromUserId.eq(rawUserId),
                        creatAtAfterStartDate(condition.startDate()),
                        creatAtBeforeEndDate(condition.endDate())
                )
                .fetchOne();
        assert total != null;
        List<SentSharedMusicDto> contents = jpaQueryFactory
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
                        sharedMusicEntity.fromUserId.eq(rawUserId),
                        creatAtAfterStartDate(condition.startDate()),
                        creatAtBeforeEndDate(condition.endDate())
                )
                .offset(offset)
                .limit(condition.size())
                .fetch()
                .stream()
                .map(SentSharedMusicProjection::toDto)
                .toList();
        return new Page<>(condition.page(), condition.size(), total, contents);
    }

    private BooleanExpression creatAtAfterStartDate(@Nullable LocalDateTime startDate) {
        if (startDate == null) {
            return null;
        }
        return sharedMusicEntity.createdAt.after(startDate);
    }

    private BooleanExpression creatAtBeforeEndDate(@Nullable LocalDateTime endDate) {
        if (endDate == null) {
            return null;
        }
        return sharedMusicEntity.createdAt.before(endDate);
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
