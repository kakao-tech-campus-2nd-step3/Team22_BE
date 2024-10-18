package io.github.eappezo.soundary.services.friend.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntityKey;
import io.github.eappezo.soundary.core.persistence.infrastructure.QFriendEntity;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepository {
    private final JpaFriendRepository jpaFriendRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean exists(FriendshipDTO friendship) {
        return jpaFriendRepository.existsById(FriendEntityKey.from(friendship));
    }

    @Override
    public int countFriends(Identifier userId) {
        QFriendEntity fromTable = new QFriendEntity("fromTable");
        QFriendEntity toTable = new QFriendEntity("toTable");
        String rawUserId = userId.toString();

        return jpaQueryFactory
                .select(fromTable.fromUserId.count())
                .from(fromTable)
                .join(toTable)
                .on(
                        fromTable.toUserId.eq(toTable.fromUserId)
                                .and(toTable.toUserId.eq(fromTable.fromUserId))
                )
                .where(fromTable.fromUserId.eq(rawUserId))
                .fetchFirst().intValue();
    }

    @Override
    public void save(FriendshipDTO friend) {
        jpaFriendRepository.save(FriendEntity.from(friend));
    }

    @Override
    public void delete(FriendshipDTO friendship) {
        jpaFriendRepository.deleteById(FriendEntityKey.from(friendship));
    }
}
