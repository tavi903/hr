FROM eclipse-temurin:21.0.2_13-jre-jammy
WORKDIR /opt/app
EXPOSE 9090
COPY target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar", "--spring.profiles.active=local"]