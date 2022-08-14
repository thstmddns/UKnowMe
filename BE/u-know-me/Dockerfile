# 그래들 빌드하는 부분
FROM gradle:7.2.0-jdk-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME/
  
COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

COPY . .
RUN gradle clean build
    
# actual container
FROM openjdk:8-jdk-alpine
ENV ARTIFACT_NAME=u-know-me-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app
    
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
    
EXPOSE 8080
ENTRYPOINT exec java -jar ${ARTIFACT_NAME} --spring.profiles.active=prod