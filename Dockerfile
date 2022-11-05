FROM openjdk:17-alpine

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} pjt-daejanggan.jar

EXPOSE 8633

ENTRYPOINT ["java", "-jar", "pjt-daejanggan.jar"]