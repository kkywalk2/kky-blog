FROM eclipse-temurin:19-alpine

# 환경변수 기본값 설정
ENV DB_USERNAME=root
ENV DB_PASSWORD=1234
ENV DB_URL=jdbc:mysql://localhost:3306/testdb
ENV DB_SHOW_SQL=false
ENV JWT_SECRET=MYSECRETKEYASDSADASDASDASDASDASDSADSADSA
ENV SERVER_PORT=9001
ENV IMAGE_PATH=/app/images
ENV CONTEXT_PATH=/blog

# 이미지 저장 디렉토리 생성
RUN mkdir -p ${IMAGE_PATH}

EXPOSE ${SERVER_PORT}
COPY ./build/libs/*.jar spring-boot-application.jar
ENTRYPOINT ["java","-jar","/spring-boot-application.jar"]