package io.github.eappezo.soundary.advice.notification;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDeviceService {
    private final UserDeviceRepository userDeviceRepository;

    @Transactional
    public boolean addDevice(String userId, String fcmToken) {
        if(userDeviceRepository.existsById(new FCMKeyManager(userId, fcmToken))){
            return false;
        }
        userDeviceRepository.deleteAllByFcmToken(fcmToken);
        userDeviceRepository.save(new UserDevice(userId, fcmToken));
        return true;
    }

    @Transactional
    public boolean removeDevice(String userId, String fcmToken) {
        if(!userDeviceRepository.existsById(new FCMKeyManager(userId, fcmToken))){
            return false;
        }
        userDeviceRepository.deleteById(new FCMKeyManager(userId, fcmToken));
        return true;
    }

    public List<String> getFcmTokensByUserId(String userId){
        return userDeviceRepository.findAllByUserId(userId)
                .stream()
                .map(UserDevice::getFcmToken)
                .toList();
    }

    @Transactional
    public void removeAllDevicesByUserId(String userId){
        userDeviceRepository.deleteAllByUserId(userId);
    }
}
