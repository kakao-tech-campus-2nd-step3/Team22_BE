imageName=eappezo/soundary-api
imageTag=0.0.1
profile=prod
serverPort=8080

echo "==== Jib 빌드 시작 ===="
echo "대상 이미지 이름: $imageName"
echo "대상 이미지 태그: $imageTag"
echo "대상 프로파일: $profile"

export IMAGE_TAG=$imageTag
export ACTIVE_PROFILE=$profile
export IMAGE_NAME=$imageName
export SERVER_PORT=$serverPort

./gradlew --stacktrace jib