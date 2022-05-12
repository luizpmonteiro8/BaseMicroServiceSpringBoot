FROM openjdk:18-jdk-slim as bulid
WORKDIR application

COPY ./user-profile-service/mvnw .
COPY ./user-profile-service/.mvn .mvn
COPY ./user-profile-service/pom.xml .
COPY ./user-profile-service/src src

RUN ./mvnw install -DskipTests

RUN cp /application/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:18-jdk-slim
WORKDIR application
COPY --from=bulid application/dependencies/ ./
COPY --from=bulid application/spring-boot-loader/ ./
COPY --from=bulid application/snapshot-dependencies/ ./
COPY --from=bulid application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]