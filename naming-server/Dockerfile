FROM openjdk:16-jdk-slim as bulid
WORKDIR application

COPY ./naming-server/mvnw .
COPY ./naming-server/.mvn .mvn
COPY ./naming-server/pom.xml .
COPY ./naming-server/src src

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
