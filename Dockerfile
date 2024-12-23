FROM openjdk:21-jdk-slim

WORKDIR /app

# Gradle wrapper와 코드 복사
COPY ./gradlew ./gradlew
COPY ./build.gradle.kts ./build.gradle.kts
COPY ./settings.gradle.kts ./settings.gradle.kts
COPY ./src ./src

# Gradle 실행 권한 부여
RUN chmod +x ./gradlew

# 의존성 캐싱
RUN ./gradlew dependencies --no-daemon

# 애플리케이션 빌드
RUN ./gradlew build --no-daemon

# 실행
CMD ["./gradlew", "bootRun"]