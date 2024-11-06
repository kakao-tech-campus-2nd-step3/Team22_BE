package io.github.eappezo.soundary.services.friend.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.QFriendEntity;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.friend.application.FriendRetrieveSupport;
import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendRequestInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.github.eappezo.soundary.core.persistence.infrastructure.QUserEntity.userEntity;
import static io.github.eappezo.soundary.core.persistence.infrastructure.QUserLabelEntity.userLabelEntity;

@Repository
@RequiredArgsConstructor
public class QFriendRetrieveSupport implements FriendRetrieveSupport {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FriendInfo> findFriends(Identifier userId, List<Label> labels) {
        QFriendEntity fromTable = new QFriendEntity("fromTable");
        QFriendEntity toTable = new QFriendEntity("toTable");
        String rawUserId = userId.toString();

        final var query = jpaQueryFactory
                .select(
                        new QFriendInfoProjection(
                                fromTable.toUserId,
                                userEntity.displayId,
                                userEntity.nickname,
                                userEntity.profileImageUrl
                        )
                )
                .from(fromTable)
                .join(toTable)
                .on(
                        fromTable.toUserId.eq(toTable.fromUserId),
                        toTable.toUserId.eq(fromTable.fromUserId),
                        fromTable.fromUserId.eq(rawUserId)
                )
                .join(userEntity)
                .on(fromTable.toUserId.eq(userEntity.id));
        if (labels.isEmpty()) {
            return query.fetch()
                    .stream()
                    .map(FriendInfoProjection::toDto)
                    .toList();
        }
        return query
                .join(userLabelEntity)
                .on(
                        userEntity.id.eq(userLabelEntity.userId),
                        userLabelEntity.label.in(labels)
                )
                .groupBy(fromTable.toUserId)
                .fetch()
                .stream()
                .map(FriendInfoProjection::toDto)
                .toList();
    }

    @Override
    public List<FriendRequestInfo> findSentRequests(Identifier userId) {
        QFriendEntity fromTable = new QFriendEntity("fromTable");
        QFriendEntity toTable = new QFriendEntity("toTable");
        String rawUserId = userId.toString();

        return jpaQueryFactory
                .select(
                        new QFriendRequestInfoProjection(
                                fromTable.toUserId,
                                userEntity.displayId,
                                userEntity.nickname,
                                userEntity.profileImageUrl
                        )
                )
                .from(fromTable)
                .leftJoin(toTable)
                .on(
                        fromTable.toUserId.eq(toTable.fromUserId),
                        fromTable.fromUserId.eq(toTable.toUserId)
                )
                .join(userEntity)
                .on(fromTable.toUserId.eq(userEntity.id))
                .where(
                        fromTable.fromUserId.eq(rawUserId),
                        toTable.fromUserId.isNull()
                )
                .fetch()
                .stream()
                .map(FriendRequestInfoProjection::toDto)
                .toList();
    }

    @Override
    public List<FriendRequestInfo> findReceivedRequests(Identifier userId) {
        QFriendEntity fromTable = new QFriendEntity("fromTable");
        QFriendEntity toTable = new QFriendEntity("toTable");
        String rawUserId = userId.toString();

        return jpaQueryFactory
                .select(
                        new QFriendRequestInfoProjection(
                                fromTable.toUserId,
                                userEntity.displayId,
                                userEntity.nickname,
                                userEntity.profileImageUrl
                        )
                )
                .from(fromTable)
                .leftJoin(toTable)
                .on(fromTable.toUserId.eq(toTable.fromUserId))
                .join(userEntity)
                .on(fromTable.fromUserId.eq(userEntity.id))
                .where(
                        fromTable.toUserId.eq(rawUserId),
                        toTable.fromUserId.isNull()
                )
                .fetch()
                .stream()
                .map(FriendRequestInfoProjection::toDto)
                .toList();
    }
}
