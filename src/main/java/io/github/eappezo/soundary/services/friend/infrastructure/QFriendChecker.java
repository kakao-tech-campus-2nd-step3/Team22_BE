package io.github.eappezo.soundary.services.friend.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntityKey;
import io.github.eappezo.soundary.core.persistence.infrastructure.QFriendEntity;
import io.github.eappezo.soundary.core.user.friend.FriendChecker;
import java.util.List;

import io.github.eappezo.soundary.services.friend.application.dto.FriendInfo;
import io.github.eappezo.soundary.services.friend.application.dto.FriendshipDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QFriendChecker implements FriendChecker {
    private final JPAQueryFactory jpaQueryFactory;
    private final JpaFriendRepository jpaFriendRepository;

    @Override
    public boolean isFriendWith(Identifier userId, List<Identifier> friendIds) {
        QFriendEntity fromTable = new QFriendEntity("from");
        QFriendEntity toTable = new QFriendEntity("to");
        String rawUserId = userId.toString();
        List<String> rawFriendIds = friendIds
            .stream()
            .map(Identifier::toString)
            .toList();
        return jpaQueryFactory.select(fromTable.toUserId.count())
            .from(fromTable)
            .join(toTable)
            .on(
                fromTable.toUserId.eq(toTable.fromUserId)
                    .and(toTable.toUserId.eq(fromTable.fromUserId))
            )
            .where(
                fromTable.fromUserId.eq(rawUserId),
                fromTable.toUserId.in(rawFriendIds)
            )
            .fetchFirst().intValue() == rawFriendIds.size();
    }


    @Override
    public boolean isFriend(Identifier userId, Identifier friendId) {
        FriendshipDTO friendship = new FriendshipDTO(userId, friendId);
        return jpaFriendRepository.existsById(FriendEntityKey.from(friendship))
               && jpaFriendRepository.existsById(FriendEntityKey.from(friendship.reverse()));
    }
}
