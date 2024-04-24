FROM openjdk:21-slim as build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
RUN ./gradlew dependencies

COPY src src
RUN ./gradlew build

FROM openjdk:21-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar e-commerce.jar
EXPOSE 8080

# default profile is dev
ENV SPRING_PROFILES_ACTIVE=dev

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "e-commerce.jar"]
