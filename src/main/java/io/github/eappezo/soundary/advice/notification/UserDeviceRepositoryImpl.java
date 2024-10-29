package io.github.eappezo.soundary.advice.notification;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.notification.UserDeviceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDeviceRepositoryImpl implements UserDeviceRepository {
    private final JpaUserDeviceRepository jpaUserDeviceRepository;

    @Override
    @Transactional
    public boolean registerDevice(Identifier userId, String deviceToken) {
        if(jpaUserDeviceRepository.existsById(FCMKey.of(userId, deviceToken))){
            return false;
        }
        jpaUserDeviceRepository.deleteAllByFcmToken(deviceToken);
        jpaUserDeviceRepository.save(new UserDevice(userId.toString(), deviceToken));
        return true;
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
    public List<String> getDevicesByUserId(String userId){
        return jpaUserDeviceRepository.findAllByUserId(userId)
                .stream()
                .map(UserDevice::getFcmToken)
                .toList();
    }

    @Override
    @Transactional
    public void removeAllDevicesByUserId(String userId){
        jpaUserDeviceRepository.deleteAllByUserId(userId);
    }
}
