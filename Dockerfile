# ---------- 1) Build stage ----------
FROM gradle:8.7-jdk17-alpine AS build
WORKDIR /workspace

# gradle wrapper + 권한
COPY gradlew ./gradlew
COPY gradle ./gradle
RUN chmod +x ./gradlew && sed -i 's/\r$//' ./gradlew

# 의존성 캐시 최적화
COPY build.gradle settings.gradle gradle.properties* ./
RUN ./gradlew --no-daemon dependencies || true

# 소스 복사 후 빌드
COPY src ./src
RUN ./gradlew --no-daemon clean bootJar -x test

# ---------- 2) Runtime stage ----------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
ENV TZ=Asia/Seoul

# 빌드 산출물 복사 (이름 패턴은 프로젝트에 맞게 조정)
COPY --from=build /workspace/build/libs/*-SNAPSHOT.jar /app/app.jar

# 런타임에 java 유무 확인
RUN java -version

EXPOSE 8080
ENTRYPOINT ["sh","-lc","exec java $JAVA_OPTS -Dserver.port=${PORT} -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]
