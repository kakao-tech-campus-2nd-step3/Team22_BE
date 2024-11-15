# Team22_BE
22조 백엔드

1. 배포 링크
Host : https://api.soundary.kro.kr<br>
API Docs : https://api.soundary.kro.kr/swagger-ui/index.html#/

2. ERD
![image](https://github.com/user-attachments/assets/3355528c-0186-4565-a4f3-5135d4e94ac7)

3. 코드 주안점
  * 인증/인가
    - 역할 기반 접근 제어 : ADMIN, USER, PENDING,LEAVED의 4가지 권한을 구분하여 기능을 제어
  * 친구
     - 친구 요청 관리 : 친구 요청을 보내고 수락/거절할 수 있는 기능 구현
     - 중복 방지 : 이미 친구 관계에 있거나 동일한 요청이 중복되는 것을 방지
     - 친구 목록 조회 : 친구 목록을 효율적으로 조회할 수 있는 최적화된 쿼리 설계
     - 푸시 알림 : 친구 신청이 오거나, 친구 신청이 수락된 경우 푸시 알림을 통한 확인 가능
  * 음악 공유
    - 공유 범위 설정 : 친구 관계에 있는 사용자들에게만 공유 가능
    - 푸시 알림 : 사용자가 음악을 공유받게 되면, 푸시 알림 전송
  * 공유된 음악 집계

