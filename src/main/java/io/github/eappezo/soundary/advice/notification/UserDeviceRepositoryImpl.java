package io.github.eappezo.soundary.advice.notification;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.UserDeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.github.eappezo.soundary.advice.notification.QUserDevice.userDevice;

@Repository
@RequiredArgsConstructor
public class UserDeviceRepositoryImpl implements UserDeviceRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final JpaUserDeviceRepository jpaUserDeviceRepository;

    @Override
    @Transactional
    public void registerDevice(Identifier userId, String deviceToken) {
        jpaUserDeviceRepository.save(new UserDevice(userId.toString(), deviceToken));
    }

    @Override
    @Transactional
    public boolean removeDevice(Identifier userId, String deviceToken) {
        if(!jpaUserDeviceRepository.existsById(FCMKey.of(userId, deviceToken))){
            return false;
        }
        jpaUserDeviceRepository.deleteById(FCMKey.of(userId, deviceToken));
        return true;
    }

    @Override
    public List<String> getDevicesByUserId(Identifier userId){
        return jpaUserDeviceRepository.findAllByUserId(userId.toString())
                .stream()
                .map(UserDevice::getFcmToken)
                .toList();
    }

    @Override
    public List<String> getDevicesByUserIds(List<Identifier> userIds) {
        List<String> rawUserIds = userIds.stream().map(Identifier::toString).toList();
        return jpaQueryFactory
                .select(userDevice.fcmToken)
                .from(userDevice)
                .where(userDevice.userId.in(rawUserIds))
                .fetch();
    }

    @Override
    @Transactional
    public void removeAllDevicesByUserId(Identifier userId){
        jpaUserDeviceRepository.deleteAllByUserId(userId.toString());
    }
}
