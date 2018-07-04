FROM openjdk:8u171-jdk-alpine3.7
LABEL maintainer="normandesjr@gmail.com"

ADD build/libs/*.jar /app/app.jar

EXPOSE 8080
HEALTHCHECK --start-period=10s --timeout=3s CMD wget -q -O /dev/null http://localhost:8080/actuator/health || exit 1

WORKDIR /app

CMD ["java", "-jar", "app.jar"]