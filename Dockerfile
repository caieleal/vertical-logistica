FROM gradle:7.6.4-jdk17-jammy AS builder

WORKDIR /app

COPY ../build.gradle.kts ../settings.gradle.kts ./
COPY ../src ./src

RUN gradle clean build -x test --no-daemon

FROM eclipse-temurin:17.0.13_11-jre-ubi9-minimal

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]