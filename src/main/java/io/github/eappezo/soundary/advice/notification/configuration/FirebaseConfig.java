package io.github.eappezo.soundary.advice.notification.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.github.eappezo.soundary.core.exception.APIException;
import io.github.eappezo.soundary.core.exception.common.CommonErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void init(){
        try{
            InputStream serviceAccount = new ClassPathResource("soundaryFCMKey.json").getInputStream();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) { // FirebaseApp이 이미 초기화되어 있지 않은 경우에만 초기화 실행
                FirebaseApp.initializeApp(options);
            }
        } catch (Exception e){
            // 수정하기 ex) APIExceptionHandler
            log.error("Firebase 초기화에 실패하였습니다.", e);
            throw new APIException(CommonErrorCode.FIREBASE_INIT_ERROR, "Firebase 초기화에 실패하였습니다.");
        }
    }
}
