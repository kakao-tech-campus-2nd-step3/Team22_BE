package io.github.eappezo.soundary.advice.notification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDeviceService {
    private final JpaUserDeviceRepository jpaUserDeviceRepository;

    @Transactional
    public boolean addDevice(String userId, String fcmToken) {
        if(jpaUserDeviceRepository.existsById(new FCMKey(userId, fcmToken))){
            return false;
        }
        jpaUserDeviceRepository.deleteAllByFcmToken(fcmToken);
        jpaUserDeviceRepository.save(new UserDevice(userId, fcmToken));
        return true;
    }

    @Transactional
    public boolean removeDevice(String userId, String fcmToken) {
        if(!jpaUserDeviceRepository.existsById(new FCMKey(userId, fcmToken))){
            return false;
        }
        jpaUserDeviceRepository.deleteById(new FCMKey(userId, fcmToken));
        return true;
    }

    public List<String> getFcmTokensByUserId(String userId){
        return jpaUserDeviceRepository.findAllByUserId(userId)
                .stream()
                .map(UserDevice::getFcmToken)
                .toList();
    }

    @Transactional
    public void removeAllDevicesByUserId(String userId){
        jpaUserDeviceRepository.deleteAllByUserId(userId);
    }
}
