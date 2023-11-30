FROM eclipse-temurin:19-alpine
EXPOSE 9001
COPY ./build/libs/*.jar spring-boot-application.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${USE_PROFILE}","/spring-boot-application.jar"]