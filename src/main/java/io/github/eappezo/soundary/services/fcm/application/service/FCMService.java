package io.github.eappezo.soundary.services.fcm.application.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.User;
import io.github.eappezo.soundary.services.user.application.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FCMService {
    @Autowired
    UserService userService;

    @Transactional
    public String getToken(Identifier userId, String token) {
        User user = userService.getUser(userId);
        // 토큰 어디 저장할지 몰라서 일단 로컬로 둘게요
        return token;
    }

    public void sendMessage(String token, String title, String body) throws FirebaseMessagingException {
        String message = FirebaseMessaging.getInstance().send(Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setToken(token)  // 대상 디바이스의 등록 토큰
                .build());

        System.out.println("Sent message: " + message);
    }
}
