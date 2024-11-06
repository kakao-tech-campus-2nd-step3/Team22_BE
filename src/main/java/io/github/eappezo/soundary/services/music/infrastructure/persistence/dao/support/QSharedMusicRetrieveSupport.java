package io.github.eappezo.soundary.services.music.infrastructure.persistence.dao.support;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.eappezo.soundary.core.Page;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.services.music.application.share.*;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static io.github.eappezo.soundary.core.persistence.infrastructure.QUserEntity.userEntity;
import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QSharedMusicEntity.sharedMusicEntity;
import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QSharedMusicLikeEntity.sharedMusicLikeEntity;
import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QSharedMusicTargetEntity.sharedMusicTargetEntity;
import static io.github.eappezo.soundary.services.music.infrastructure.persistence.QTrackEntity.trackEntity;

@Component
@RequiredArgsConstructor
public class QSharedMusicRetrieveSupport implements SharedMusicRetrieveSupport {
    private final JPAQueryFactory jpaQueryFactory;
    @Value("${app.shared-music.exposure-duration-days}") private int exposureDurationDays;

    @Override
    public Page<SentSharedMusicDto> getSentSharedMusic(
            Identifier userId,
            SharedMusicQueryCondition condition
    ) {
        long offset = condition.offset();
        String rawUserId = userId.toString();
        Long total = jpaQueryFactory
                .select(sharedMusicEntity.id.count())
                .from(sharedMusicEntity)
                .where(
                        sharedMusicEntity.fromUserId.eq(rawUserId),
                        creatAtAfterStartDate(condition.startDate()),
                        creatAtBeforeEndDate(condition.endDate()),
                        isWithinExposureDuration(condition.onlyExposured())
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
                .orderBy(sharedMusicEntity.id.desc())
                .offset(offset)
                .limit(condition.size())
                .fetch()
                .stream()
                .map(SentSharedMusicProjection::toDto)
                .toList();
        return new Page<>(condition.page(), condition.size(), total, contents);
    }

    @Override
    public Page<ReceivedSharedMusicDto> getReceivedSharedMusic(
            Identifier userId,
            SharedMusicQueryCondition condition
    ) {
        long offset = condition.offset();
        String rawUserId = userId.toString();
        Long total = jpaQueryFactory
                .select(sharedMusicTargetEntity.targetUserId.count())
                .from(sharedMusicTargetEntity)
                .join(sharedMusicEntity)
                .on(sharedMusicTargetEntity.sharedMusicId.eq(sharedMusicEntity.id))
                .where(
                        sharedMusicTargetEntity.targetUserId.eq(rawUserId),
                        creatAtAfterStartDate(condition.startDate()),
                        creatAtBeforeEndDate(condition.endDate()),
                        isWithinExposureDuration(condition.onlyExposured())
                )
                .fetchOne();
        assert total != null;
        List<ReceivedSharedMusicDto> contents = jpaQueryFactory
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
                                sharedMusicLikeEntity.likedUserId.isNotNull(),
                                sharedMusicEntity.createdAt
                        )
                )
                .from(sharedMusicTargetEntity)
                .join(sharedMusicEntity)
                .on(
                        sharedMusicTargetEntity.sharedMusicId.eq(sharedMusicEntity.id),
                        sharedMusicTargetEntity.targetUserId.eq(rawUserId)
                )
                .join(trackEntity)
                .on(sharedMusicEntity.trackId.eq(trackEntity.id))
                .join(userEntity)
                .on(sharedMusicEntity.fromUserId.eq(userEntity.id))
                .leftJoin(sharedMusicLikeEntity)
                .on(
                        sharedMusicLikeEntity.sharedMusicId.eq(sharedMusicEntity.id),
                        sharedMusicLikeEntity.likedUserId.eq(rawUserId)
                )
                .orderBy(sharedMusicEntity.id.desc())
                .offset(offset)
                .limit(condition.size())
                .fetch()
                .stream()
                .map(ReceivedSharedMusicProjection::toDto)
                .toList();
        return new Page<>(condition.page(), condition.size(), total, contents);
    }

    @Override
    public SharedMusicLikesDto getSharedMusicLikes(Identifier sharedMusicId) {
        int likes = jpaQueryFactory
                .select(sharedMusicLikeEntity.likedUserId.count())
                .from(sharedMusicLikeEntity)
                .where(sharedMusicLikeEntity.sharedMusicId.eq(sharedMusicId.toString()))
                .fetchOne().intValue();
        List<SharedMusicLikesDto.LikedUser> likedUsers = jpaQueryFactory
                .select(
                        new QSharedMusicLikedUserProjection(
                                sharedMusicLikeEntity.likedUserId,
                                userEntity.displayId,
                                userEntity.nickname,
                                userEntity.profileImageUrl
                        )
                )
                .from(sharedMusicLikeEntity)
                .join(userEntity)
                .on(sharedMusicLikeEntity.likedUserId.eq(userEntity.id))
                .where(sharedMusicLikeEntity.sharedMusicId.eq(sharedMusicId.toString()))
                .fetch()
                .stream()
                .map(SharedMusicLikedUserProjection::toLikedUser)
                .toList();
        return new SharedMusicLikesDto(likes, likedUsers);
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

    private BooleanExpression isWithinExposureDuration(boolean onlyExposured) {
        if (!onlyExposured) {
            return null;
        }
        return sharedMusicEntity
                .createdAt
                .after(LocalDateTime.now().minusDays(exposureDurationDays));
    }
}
