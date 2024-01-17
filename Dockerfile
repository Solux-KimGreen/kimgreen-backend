# docker에서 받아서 쓸 base img
FROM amazoncorretto:17.0.6
# JAR_FILE 변수설정 : .jar 파일 위치
ARG JAR_FILE=build/libs/backend-0.0.1-SNAPSHOT.jar
# 위에서 할당한 .jar를 app.jar이름으로 copy
COPY ${JAR_FILE} app.jar
# java 실행 시 parameter
# executable : java
# param : -jar, /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 1. Dockerfile 기반 이미지 생성
#docker build -t gol2580/kimgreen .

# 2. 이미지 기반 컨테이너 실행
#  docker run -d -p 8080:8080 gol2580/kimgreen

# 3. docker hub에 push
# docker push gol2580/kimgreen
# test1