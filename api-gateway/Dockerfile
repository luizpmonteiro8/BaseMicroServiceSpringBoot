FROM openjdk:16-jdk-slim as bulid
WORKDIR application

COPY ./api-gateway/mvnw .
COPY ./api-gateway/.mvn .mvn
COPY ./api-gateway/pom.xml .
COPY ./api-gateway/src src

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
