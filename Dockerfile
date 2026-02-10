FROM eclipse-temurin:21-jdk-alpine

# 환경변수 기본값 설정
ENV DB_USERNAME=root
ENV DB_PASSWORD=1234
ENV DB_URL=jdbc:mysql://localhost:3306/testdb
ENV DB_SHOW_SQL=false
ENV JWT_SECRET=MYSECRETKEYASDSADASDASDASDASDASDSADSADSA
ENV SERVER_PORT=9001
ENV IMAGE_PATH=/app/images
ENV CONTEXT_PATH=/blog
ENV USE_PROFILE=prod

# 이미지 저장 디렉토리 생성
RUN mkdir -p ${IMAGE_PATH}

EXPOSE ${SERVER_PORT}
COPY ./build/libs/*.jar spring-boot-application.jar
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${USE_PROFILE} -jar /spring-boot-application.jar"]
