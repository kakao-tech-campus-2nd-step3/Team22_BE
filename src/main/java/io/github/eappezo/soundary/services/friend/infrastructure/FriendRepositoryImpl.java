package io.github.eappezo.soundary.services.friend.infrastructure;

import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntity;
import io.github.eappezo.soundary.core.persistence.infrastructure.FriendEntityKey;
import io.github.eappezo.soundary.services.friend.application.FriendRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepository {
    private final JpaFriendRepository jpaFriendRepository;

    @Override
    public Optional<FriendEntity> findById(FriendEntityKey friendKey) {
        return jpaFriendRepository.findById(friendKey);
    }

    @Override
    public void save(FriendEntity friend) {
        jpaFriendRepository.save(friend);
    }

    @Override
    public void deleteById(FriendEntityKey friendKey) {
        jpaFriendRepository.deleteById(friendKey);
    }

    @Override
    public List<FriendEntity> findByFromUserId(String fromUserId) {
        return jpaFriendRepository.findByFromUserId(fromUserId);
    }

    @Override
    public List<FriendEntity> findByToUserId(String toUserId) {
        return jpaFriendRepository.findByToUserId(toUserId);
    }

    @Override
    public List<FriendEntity> findFriend(String fromUserId) {
        return findFriend(fromUserId);
    }

    @Override
    public List<FriendEntity> findReceivedRequest(String toUserId) {
        return findReceivedRequest(toUserId);
    }

    @Override
    public List<FriendEntity> findSentRequest(String fromUserId) {
        return findSentRequest(fromUserId);
    }
}
