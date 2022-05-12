FROM openjdk:16-jdk-slim as bulid
WORKDIR application

COPY ./oauth-service/mvnw .
COPY ./oauth-service/.mvn .mvn
COPY ./oauth-service/pom.xml .
COPY ./oauth-service/src src

RUN ./mvnw install -DskipTests

RUN cp /application/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:16-jdk-slim
WORKDIR application
COPY --from=bulid application/dependencies/ ./
COPY --from=bulid application/spring-boot-loader/ ./
COPY --from=bulid application/snapshot-dependencies/ ./
COPY --from=bulid application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]