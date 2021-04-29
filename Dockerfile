# Dockerfile for Purple Cow App. You don't even need java on your system, this dockerfile uses a multi-stage build to
# build the code in a gradle container, then just copies the build artifacts into a slim java container.

# Stage 1 - build container
FROM gradle:6.8 AS build-container
WORKDIR /home/gradle
COPY src /home/gradle/src
COPY build.gradle settings.gradle /home/gradle/
RUN gradle bootJar

# Stage 2 - Actual container, just copying the built JAR
FROM openjdk:8-jre-slim
RUN mkdir -p /opt/erittenhouse/purple-cow-app/
WORKDIR /opt/erittenhouse/purple-cow-app/
COPY --from=build-container /home/gradle/build/libs/PurpleCowApp*.jar ./PurpleCowApp.jar
CMD java -jar PurpleCowApp.jar
